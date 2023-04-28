package com.x.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.x.base.exception.XueChengException;
import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.base.model.RestResponse;
import com.x.media.mapper.MediaFilesMapper;
import com.x.media.mapper.MediaProcessMapper;
import com.x.media.model.dto.QueryMediaParamsDto;
import com.x.media.model.dto.UploadFileParamsDto;
import com.x.media.model.dto.UploadFileResultDto;
import com.x.media.model.po.MediaFiles;
import com.x.media.model.po.MediaProcess;
import com.x.media.service.MediaFileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @description TODO
 * @author Mr.M
 * @date 2022/9/10 8:58
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

    private final MediaFilesMapper mediaFilesMapper;
    private final MinioClient minioClient;
    private final MediaProcessMapper mediaProcessMapper;
    public static final String PARAM = "/";
    /**
     * 普通文件存储的桶
     */
    @Value("${minio.bucket.files}")
    private String bucketFiles;
    /**
     * 视频文件存储的桶
     */
    @Value("${minio.bucket.videofiles}")
    private String bucketVideoFiles;
    @Autowired
    MediaFileService currentProxy;

    @Override
    public PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {

        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();

        //分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        return new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());

    }


    @Override

    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName) {

        //得到文件的md5值
        String fileMd5 = DigestUtils.md5Hex(bytes);

        if (StringUtils.isEmpty(folder)) {
            //自动生成目录的路径 按年月日生成，
            folder = getFileFolder(new Date(), true, true, true);
        } else if (!folder.contains(PARAM)) {
            folder = folder + PARAM;
        }
        //文件名称
        String filename = uploadFileParamsDto.getFilename();

        if (StringUtils.isEmpty(objectName)) {
            //如果objectName为空，使用文件的md5值为objectName
            objectName = fileMd5 + filename.substring(filename.lastIndexOf("."));
        }
        objectName = folder + objectName;

        try {

            addMediaFilesToMinIO(bytes, bucketFiles, objectName);
            MediaFiles mediaFiles = currentProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketFiles, objectName);
            //准备返回数据
            UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
            BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);
            return uploadFileResultDto;
        } catch (Exception e) {
            log.debug("上传文件失败：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }


    }

    /**将文件信息入库
     * @param companyId 机构id
     * @param fileId 文件id
     * @param uploadFileParamsDto 上传对象
     * @param bucket 桶
     * @param objectName 文件名
     * @return 文件对象
     */
    @Transactional(rollbackFor = Exception.class)
    public MediaFiles addMediaFilesToDb(Long companyId, String fileId, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        //保存到数据库
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();

            //封装数据
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            mediaFiles.setId(fileId);
            mediaFiles.setFileId(fileId);
            mediaFiles.setCompanyId(companyId);
            mediaFiles.setBucket(bucket);
            mediaFiles.setFilePath(objectName);

            //获取扩展名
            String extension = null;
            String filename = uploadFileParamsDto.getFilename();
            if(StringUtils.isNotEmpty(filename) && filename.contains(".")){
                extension = filename.substring(filename.lastIndexOf("."));
            }
            //媒体类型
            String mimeType = getMimeTypeByextension(extension);
            //图片、mp4视频可以设置URL
            if(mimeType.contains("image") || mimeType.contains("mp4")){
                mediaFiles.setUrl(PARAM + bucket + PARAM + objectName);
            }

            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setStatus("1");
            mediaFiles.setAuditStatus("002003");


            //插入文件表
            mediaFilesMapper.insert(mediaFiles);

            //对avi视频添加到待处理任务表
            if("video/x-msvideo".equals(mimeType)){

                MediaProcess mediaProcess = new MediaProcess();
                BeanUtils.copyProperties(mediaFiles,mediaProcess);
                //设置一个状态
                mediaProcess.setStatus("1");//未处理
                mediaProcessMapper.insert(mediaProcess);
            }



        }
        return mediaFiles;
    }

    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {

        //在文件表存在，并且在文件系统存在，此文件才存在
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if(mediaFiles==null){
            return RestResponse.success(false);
        }
        //查看是否在文件系统存在
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(mediaFiles.getBucket()).object(mediaFiles.getFilePath()).build();
        try {
            InputStream inputStream = minioClient.getObject(getObjectArgs);
            if(inputStream==null){
                //文件不存在
                return RestResponse.success(false);
            }
        }catch (Exception e){
            //文件不存在
            return RestResponse.success(false);
        }
        //文件已存在
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex) {

        //得到分块文件所在目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunkIndex;

        //查询文件系统分块文件是否存在
        //查看是否在文件系统存在
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketVideoFiles).object(chunkFilePath).build();
        try {
            InputStream inputStream = minioClient.getObject(getObjectArgs);
            if(inputStream==null){
                //文件不存在
                return RestResponse.success(false);
            }
        }catch (Exception e){
            //文件不存在
            return RestResponse.success(false);
        }


        return RestResponse.success(true);
    }

    @Override
    public RestResponse uploadChunk(String fileMd5, int chunk, byte[] bytes) {

        //得到分块文件所在目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunk;

        try {
            //将分块上传到文件系统
            addMediaFilesToMinIO(bytes, bucketVideoFiles, chunkFilePath);
            //上传成功
            return RestResponse.success(true);
        } catch (Exception e) {
            log.debug("上传分块文件失败：{}", e.getMessage());
            return RestResponse.validfail(false,"上传分块失败");
        }

    }

    //合并分块
    @Override
    public RestResponse mergechunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        //下载分块
        File[] chunkFiles = checkChunkStatus(fileMd5, chunkTotal);

        //得到合并后文件的扩展名
        String filename = uploadFileParamsDto.getFilename();
        //扩展名
        String extension = filename.substring(filename.lastIndexOf("."));
        File tempMergeFile = null;
        try {
            try {
                //创建一个临时文件作为合并文件
                tempMergeFile = File.createTempFile("'merge'", extension);
            } catch (IOException e) {
                XueChengException.cast("创建临时合并文件出错");
            }

            //创建合并文件的流对象
            try( RandomAccessFile rafWrite  =new RandomAccessFile(tempMergeFile, "rw")) {
                byte[] b = new byte[1024];
                for (File file : chunkFiles) {
                    //读取分块文件的流对象
                    try(RandomAccessFile rafRead = new RandomAccessFile(file, "r");) {
                        int len = -1;
                        while ((len = rafRead.read(b)) != -1) {
                            //向合并文件写数据
                            rafWrite.write(b, 0, len);
                        }
                    }

                }
            } catch (IOException e) {
                XueChengException.cast("合并文件过程出错");
            }


            //校验合并后的文件是否正确
            try {
                FileInputStream mergeFileStream = new FileInputStream(tempMergeFile);
                String mergeMd5Hex = DigestUtils.md5Hex(mergeFileStream);
                if (!fileMd5.equals(mergeMd5Hex)) {
                    log.debug("合并文件校验不通过,文件路径:{},原始文件md5:{}", tempMergeFile.getAbsolutePath(), fileMd5);
                    XueChengException.cast("合并文件校验不通过");
                }
            } catch (IOException e) {
                log.debug("合并文件校验出错,文件路径:{},原始文件md5:{}", tempMergeFile.getAbsolutePath(), fileMd5);
                XueChengException.cast("合并文件校验出错");
            }


            //拿到合并文件在minio的存储路径
            String mergeFilePath = getFilePathByMd5(fileMd5, extension);
            //将合并后的文件上传到文件系统
            addMediaFilesToMinIO(tempMergeFile.getAbsolutePath(), bucketVideoFiles, mergeFilePath);

            //将文件信息入库保存
            uploadFileParamsDto.setFileSize(tempMergeFile.length());//合并文件的大小
            addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketVideoFiles, mergeFilePath);

            return RestResponse.success(true);
        }finally {
            //删除临时分块文件
            for (File chunkFile : chunkFiles) {
                if (chunkFile.exists()) {
                    chunkFile.delete();
                }
            }
            //删除合并的临时文件
            if(tempMergeFile!=null){
                tempMergeFile.delete();
            }


        }
    }

    @Override
    public MediaFiles getFileById(String id) {
        MediaFiles mediaFiles = mediaFilesMapper.selectById(id);
        if(mediaFiles==null){
            XueChengException.cast("文件不存在");
        }
        String url = mediaFiles.getUrl();
        if(StringUtils.isEmpty(url)){
            XueChengException.cast("文件还没有处理，请稍后预览");
        }

        return mediaFiles;
    }

    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.substring(0,1) + PARAM + fileMd5.substring(1,2) + PARAM + fileMd5 + PARAM +fileMd5 +fileExt;
    }
    /**
     * @description 下载分块
     * @param chunkTotal 分块数量
     * @return java.io.File[] 分块文件数组
     * @author Mr.M
     * @date 2022/10/14 15:07
     */
    private File[] checkChunkStatus(String fileMd5,int chunkTotal ){

        //得到分块文件所在目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //分块文件数组
        File[] chunkFiles = new File[chunkTotal];
        //开始下载
        for (int i = 0; i < chunkTotal; i++) {
            //分块文件的路径
            String chunkFilePath = chunkFileFolderPath + i;
            //分块文件
            File chunkFile = null;
            try {
                chunkFile = File.createTempFile("chunk", null);
            } catch (IOException e) {
                e.printStackTrace();
                XueChengException.cast("创建分块临时文件出错"+e.getMessage());
            }

            //下载分块文件
            downloadFileFromMinio(chunkFile, bucketVideoFiles, chunkFilePath);
            chunkFiles[i] = chunkFile;

        }

        return chunkFiles;

    }

    public File downloadFileFromMinio(File file,String bucket,String objectName){

        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucket).object(objectName).build();
        try(
                InputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream =new FileOutputStream(file);
        ) {
            IOUtils.copy(inputStream,outputStream);
            return file;
        }catch (Exception e){
            e.printStackTrace();
            XueChengException.cast("查询分块文件出错");
        }
        return null;
    }

    //得到分块文件的目录
    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.substring(0, 1) + PARAM + fileMd5.substring(1, 2) + PARAM + fileMd5 + PARAM + "chunk" + PARAM;
    }

    //将文件上传到文件系统
    public void addMediaFilesToMinIO(String filePath, String bucket, String objectName){
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .filename(filePath)
                    .build();
            //上传
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("文件上传成功:{}",filePath);
        } catch (Exception e) {
            XueChengException.cast("文件上传到文件系统失败");
        }
    }
    //将文件上传到分布式文件系统
    private void addMediaFilesToMinIO(byte[] bytes, String bucket, String objectName) {

        //资源的媒体类型 默认未知二进制流
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        if (objectName.contains(".")) {
            //取objectName中的扩展名
            String extension = objectName.substring(objectName.lastIndexOf("."));
            ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
            if (extensionMatch != null) {
                contentType = extensionMatch.getMimeType();
            }

        }

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    //InputStream stream, long objectSize 对象大小, long partSize 分片大小(-1表示5M,最大不要超过5T，最多10000)
                    .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
                    .contentType(contentType)
                    .build();
            //上传到minio
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("上传文件到文件系统出错:{}", e.getMessage());
            XueChengException.cast("上传文件到文件系统出错");
        }
    }

    //根据扩展名拿匹配的媒体类型
    private String getMimeTypeByextension(String extension){
        //资源的媒体类型
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//默认未知二进制流
        if(StringUtils.isNotEmpty(extension)){
            ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
            if (extensionMatch != null) {
                contentType = extensionMatch.getMimeType();
            }
        }
        return  contentType;
    }

    //根据日期拼接目录
    private String getFileFolder(Date date, boolean year, boolean month, boolean day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期字符串
        String dateString = sdf.format(new Date());
        //取出年、月、日
        String[] dateStringArray = dateString.split("-");
        StringBuilder folderString = new StringBuilder();
        if (year) {
            folderString.append(dateStringArray[0]);
            folderString.append(PARAM);
        }
        if (month) {
            folderString.append(dateStringArray[1]);
            folderString.append(PARAM);
        }
        if (day) {
            folderString.append(dateStringArray[2]);
            folderString.append(PARAM);
        }
        return folderString.toString();
    }

    //    @Override
//    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName) {
//
//
//        //得到文件的md5值
//        String fileMd5 = DigestUtils.md5Hex(bytes);
//
//        if(StringUtils.isEmpty(folder)){
//            //自动生成目录的路径 按年月日生成，
//            folder = getFileFolder(new Date(), true, true, true);
//        }else if(folder.indexOf(PARAM)<0){
//            folder = folder+PARAM;
//        }
//        //文件名称
//        String filename = uploadFileParamsDto.getFilename();
//
//        if(StringUtils.isEmpty(objectName)){
//            //如果objectName为空，使用文件的md5值为objectName
//            objectName = fileMd5 + filename.substring(filename.lastIndexOf("."));
//        }
//
//        objectName = folder + objectName;
//
//        try {
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            String contentType = uploadFileParamsDto.getContentType();
//
//            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//                    .bucket(bucket_files)
//                    .object(objectName)
//                    //InputStream stream, long objectSize 对象大小, long partSize 分片大小(-1表示5M,最大不要超过5T，最多10000)
//                    .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
//                    .contentType(contentType)
//                    .build();
//            //上传到minio
//            minioClient.putObject(putObjectArgs);
//
//            //保存到数据库
//            MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
//            if(mediaFiles == null){
//                mediaFiles = new MediaFiles();
//
//                //封装数据
//                BeanUtils.copyProperties(uploadFileParamsDto,mediaFiles);
//                mediaFiles.setId(fileMd5);
//                mediaFiles.setFileId(fileMd5);
//                mediaFiles.setCompanyId(companyId);
//                mediaFiles.setFilename(filename);
//                mediaFiles.setBucket(bucket_files);
//                mediaFiles.setFilePath(objectName);
//                mediaFiles.setUrl(PARAM+bucket_files+PARAM+objectName);
//                mediaFiles.setCreateDate(LocalDateTime.now());
//                mediaFiles.setStatus("1");
//                mediaFiles.setAuditStatus("002003");
//
//                //插入文件表
//                mediaFilesMapper.insert(mediaFiles);
//
//            }
//
//            //准备返回数据
//            UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
//            BeanUtils.copyProperties(mediaFiles,uploadFileResultDto);
//            return uploadFileResultDto;
//
//
//        } catch (Exception e) {
//            log.debug("上传文件失败：{}",e.getMessage());
//        }
//
//        return null;
//    }
}
