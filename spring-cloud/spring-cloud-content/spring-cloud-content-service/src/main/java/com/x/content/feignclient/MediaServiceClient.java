package com.x.content.feignclient;

import com.x.base.model.RestResponse;
import com.x.content.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description TODO
 * @author Mr.M
 * @date 2022/10/19 9:24
 * @version 1.0
 */
@FeignClient(value = "media-api",url = "http://localhost:63050",configuration = MultipartSupportConfig.class,fallbackFactory = MediaServiceClientFallbackFactory.class)
@RequestMapping("/media")
public interface MediaServiceClient {

 @RequestMapping(value = "/upload/coursefile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
 public String upload(@RequestPart("filedata") MultipartFile filedata,
                      @RequestParam(value = "folder",required=false) String folder,
                      @RequestParam(value= "objectName",required=false) String objectName) ;

}
