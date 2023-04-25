package com.x.media.api;

import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.media.model.dto.QueryMediaParamsDto;
import com.x.media.model.po.MediaFiles;
import com.x.media.service.MediaFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}