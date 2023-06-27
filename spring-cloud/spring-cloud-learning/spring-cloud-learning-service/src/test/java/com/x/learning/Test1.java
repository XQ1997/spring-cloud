package com.x.learning;

import com.x.base.model.PageResult;
import com.x.content.model.po.CoursePublish;
import com.x.learning.feignclient.ContentServiceClient;
import com.x.learning.model.dto.MyCourseTableItemDto;
import com.x.learning.model.dto.MyCourseTableParams;
import com.x.learning.service.MyCourseTablesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description TODO
 * @author Mr.M
 * @date 2022/10/2 10:32
 * @version 1.0
 */
 @SpringBootTest
public class Test1 {

  @Autowired
 ContentServiceClient contentServiceClient;

  @Autowired
    MyCourseTablesService myCourseTablesService;

  @Test
 public void test(){
   CoursePublish coursepublish = contentServiceClient.getCoursepublish(18L);
   System.out.println(coursepublish);
  }
  @Test
 public void test2(){
      MyCourseTableParams myCourseTableParams = new MyCourseTableParams();
      myCourseTableParams.setUserId("52");
      PageResult<MyCourseTableItemDto> mycourestabls = myCourseTablesService.mycourestabls(myCourseTableParams);
      System.out.println(mycourestabls);
  }

}
