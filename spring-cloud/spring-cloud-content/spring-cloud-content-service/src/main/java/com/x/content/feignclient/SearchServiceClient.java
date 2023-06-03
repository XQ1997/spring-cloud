package com.x.content.feignclient;

import com.x.content.feignclient.model.CourseIndex;
import com.x.content.feignclient.model.SearchServiceClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 搜索服务远程调用接口
 * @author Mr.M
 * @date 2022/10/19 9:24
 * @version 1.0
 */
@FeignClient(value = "search",fallbackFactory = SearchServiceClientFallbackFactory.class)
@RequestMapping("/search")
public interface SearchServiceClient {

 @PostMapping("/index/course")
 public Boolean add(@RequestBody CourseIndex courseIndex);

 }
