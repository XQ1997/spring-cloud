package com.x.media.api;

import com.x.base.exception.XueChengException;
import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.base.model.RestResponse;
import com.x.media.model.dto.QueryMediaParamsDto;
import com.x.media.model.dto.UploadFileParamsDto;
import com.x.media.model.dto.UploadFileResultDto;
import com.x.media.model.po.MediaFiles;
import com.x.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 媒资文件管理接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
 @Api(value = "媒资文件管理接口",tags = "媒资文件管理接口")
 @RestController
 @RequiredArgsConstructor
public class MediaFilesController {

      private final MediaFileService mediaFileService;

     @ApiOperation("媒资列表查询接口")
     @PostMapping("/files")
     public PageResult<MediaFiles> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto){
          Long companyId = 1232141425L;
          return mediaFileService.queryMediaFiles(companyId,pageParams,queryMediaParamsDto);
     }

    /*@ApiOperation("上传文件")
    @RequestMapping(value = "/upload/coursefile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadFileResultDto upload(@RequestPart("filedata") MultipartFile filedata,
                                      @RequestParam(value = "folder",required=false) String folder,
                                      @RequestParam(value= "objectName",required=false) String objectName) {

        System.out.println("haha");
        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        String contentType = filedata.getContentType();
        uploadFileParamsDto.setContentType(contentType);
        uploadFileParamsDto.setFileSize(filedata.getSize());//文件大小
        if (contentType.indexOf("image") >= 0) {
            //是个图片
            uploadFileParamsDto.setFileType("001001");
        } else {
            uploadFileParamsDto.setFileType("001003");
        }
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());//文件名称
        UploadFileResultDto uploadFileResultDto = null;
        try {
            uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, filedata.getBytes(), folder, objectName);
        } catch (Exception e) {
            XueChengException.cast("上传文件过程中出错");
        }

        return uploadFileResultDto;

    }*/

    @ApiOperation("上传文件")
    @RequestMapping(value = "/upload/coursefile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(@RequestPart("filedata") MultipartFile filedata,
                                      @RequestParam(value = "folder",required=false) String folder,
                                      @RequestParam(value= "objectName",required=false) String objectName) {

        System.out.println("haha");
        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        String contentType = filedata.getContentType();
        uploadFileParamsDto.setContentType(contentType);
        uploadFileParamsDto.setFileSize(filedata.getSize());//文件大小
        if (contentType.indexOf("image") >= 0) {
            //是个图片
            uploadFileParamsDto.setFileType("001001");
        } else {
            uploadFileParamsDto.setFileType("001003");
        }
        uploadFileParamsDto.setFilename(filedata.getOriginalFilename());//文件名称
        UploadFileResultDto uploadFileResultDto = null;
        try {
            uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, filedata.getBytes(), folder, objectName);
        } catch (Exception e) {
            XueChengException.cast("上传文件过程中出错");
        }

        return "haha";

    }

    @ApiOperation("预览文件")
    @GetMapping("/preview/{mediaId}")
    public RestResponse<String> getPlayUrlByMediaId(@PathVariable String mediaId){

        //调用service查询文件的url

        MediaFiles mediaFiles = mediaFileService.getFileById(mediaId);
        return RestResponse.success(mediaFiles.getUrl());
    }

    @GetMapping("/greeting")
    public String getGreeting() {
        System.out.println("ss");
        return "hello";
    }
}