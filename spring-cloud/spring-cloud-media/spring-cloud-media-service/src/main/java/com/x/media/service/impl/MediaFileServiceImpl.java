package com.x.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.x.base.exception.XueChengException;
import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.media.mapper.MediaFilesMapper;
import com.x.media.model.dto.QueryMediaParamsDto;
import com.x.media.model.dto.UploadFileParamsDto;
import com.x.media.model.dto.UploadFileResultDto;
import com.x.media.model.po.MediaFiles;
import com.x.media.service.MediaFileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
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

        //生成文件id，文件的md5值
        String fileId = DigestUtils.md5Hex(bytes);
        //文件名称
        String filename = uploadFileParamsDto.getFilename();
        //构造objectName
        if (StringUtils.isEmpty(objectName)) {
            objectName = fileId + filename.substring(filename.lastIndexOf("."));
        }
        if (StringUtils.isEmpty(folder)) {
            //通过日期构造文件存储路径
            folder = getFileFolder(new Date(), true, true, true);
        } else if (!folder.contains(PARAM)) {
            folder = folder + PARAM;
        }
        //对象名称
        objectName = folder + objectName;
        MediaFiles mediaFiles = null;
        try {
            //上传至文件系统
            addMediaFilesToMinio(bytes,bucketFiles,objectName);
            //写入文件表
            mediaFiles = currentProxy.addMediaFilesToDatabase(companyId,fileId,uploadFileParamsDto,bucketFiles,objectName);
            UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
            BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);
            return uploadFileResultDto;
        } catch (Exception e) {
            e.printStackTrace();
            XueChengException.cast("上传过程中出错");
        }
        return null;

    }

    /**
     * @description 将文件写入minIO
     * @param bytes  文件字节数组
     * @param bucket  桶
     * @param objectName 对象名称
     * @author Mr.M
     * @date 2022/10/12 21:22
     */
    public void addMediaFilesToMinio(byte[] bytes, String bucket, String objectName) {
        //转为流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        //扩展名
        String extension = null;
        if(objectName.contains(".")){
            //文件扩展名
            extension = objectName.substring(objectName.lastIndexOf("."));
        }
        String  contentType = getMimeTypeByExtension(extension);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucket).object(objectName)
                    //-1表示文件分片按5M(不小于5M,不大于5T),分片数量最大10000，
                    .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
                    .contentType(contentType)
                    .build();

            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            XueChengException.cast("上传文件到文件系统出错");
        }
    }

    private String getMimeTypeByExtension(String extension){
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(StringUtils.isNotEmpty(extension)){
            ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
            if(extensionMatch!=null){
                contentType = extensionMatch.getMimeType();
            }
        }
        return contentType;

    }

    /**
     * @description 将文件信息添加到文件表 写入数据库  事务做细
     * @param companyId  机构id
     * @param fileMd5  文件md5值
     * @param uploadFileParamsDto  上传文件的信息
     * @param bucket  桶
     * @param objectName 对象名称
     * @return com.xuecheng.media.model.po.MediaFiles
     * @author Mr.M
     * @date 2022/10/12 21:22
     */
    @Transactional
    public MediaFiles addMediaFilesToDatabase(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName){
        //从数据库查询文件
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();
            //拷贝基本信息
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            mediaFiles.setId(fileMd5);
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setCompanyId(companyId);
            mediaFiles.setUrl(PARAM + bucket + PARAM + objectName);
            mediaFiles.setBucket(bucket);
            mediaFiles.setFilePath(objectName);
            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setAuditStatus("002003");
            mediaFiles.setStatus("1");
            //保存文件信息到文件表
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert < 0) {
                XueChengException.cast("保存文件信息失败");
            }

        }
        return mediaFiles;

    }

    /**根据日期拼接目录
     * @param date  时间
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 目录
     */
    private String getFileFolder(Date date, boolean year, boolean month, boolean day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期字符串
        String dateString = sdf.format(new Date());
        //取出年、月、日
        String[] dateStringArray = dateString.split("-");
        StringBuilder folderString = new StringBuilder();
        if(year){
            folderString.append(dateStringArray[0]);
            folderString.append(PARAM);
        }
        if(month){
            folderString.append(dateStringArray[1]);
            folderString.append(PARAM);
        }
        if(day){
            folderString.append(dateStringArray[2]);
            folderString.append(PARAM);
        }
        return folderString.toString();
    }

    /*@Override 优化前代码
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, byte[] bytes, String folder, String objectName) {

        //得到文件的md5值
        String fileMd5 = DigestUtils.md5Hex(bytes);

        if(StringUtils.isEmpty(folder)){
            //自动生成目录的路径 按年月日生成，
            folder = getFileFolder(new Date(), true, true, true);
        }else if(folder.indexOf(PARAM)<0){
            folder = folder+PARAM;
        }
        //文件名称
        String filename = uploadFileParamsDto.getFilename();

        if(StringUtils.isEmpty(objectName)){
            //如果objectName为空，使用文件的md5值为objectName
            objectName = fileMd5 + filename.substring(filename.lastIndexOf("."));
        }

        objectName = folder + objectName;

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            String contentType = uploadFileParamsDto.getContentType();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketFiles)
                    .object(objectName)
                    //InputStream stream, long objectSize 对象大小, long partSize 分片大小(-1表示5M,最大不要超过5T，最多10000)
                    .stream(byteArrayInputStream, byteArrayInputStream.available(), -1)
                    .contentType(contentType)
                    .build();
            //上传到minio
            minioClient.putObject(putObjectArgs);

            //保存到数据库
            MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
            if(mediaFiles == null){
                mediaFiles = new MediaFiles();

                //封装数据
                BeanUtils.copyProperties(uploadFileParamsDto,mediaFiles);
                mediaFiles.setId(fileMd5);
                mediaFiles.setFileId(fileMd5);
                mediaFiles.setCompanyId(companyId);
                mediaFiles.setFilename(filename);
                mediaFiles.setBucket(bucketFiles);
                mediaFiles.setFilePath(objectName);
                mediaFiles.setUrl(PARAM+bucketFiles+PARAM+objectName);
                mediaFiles.setCreateDate(LocalDateTime.now());
                mediaFiles.setStatus("1");
                mediaFiles.setAuditStatus("002003");

                //插入文件表
                mediaFilesMapper.insert(mediaFiles);

            }

            //准备返回数据
            UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
            BeanUtils.copyProperties(mediaFiles,uploadFileResultDto);
            return uploadFileResultDto;


        } catch (Exception e) {
            log.debug("上传文件失败：{}",e.getMessage());
        }

        return null;
    }*/
}
