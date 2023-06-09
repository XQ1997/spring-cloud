package com.x.learning.api;

import com.x.base.exception.XueChengException;
import com.x.base.model.PageResult;
import com.x.learning.model.dto.MyCourseTableItemDto;
import com.x.learning.model.dto.MyCourseTableParams;
import com.x.learning.model.dto.XcChooseCourseDto;
import com.x.learning.model.dto.XcCourseTablesDto;
import com.x.learning.model.po.XcCourseTables;
import com.x.learning.service.MyCourseTablesService;
import com.x.learning.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.M
 * @version 1.0
 * @description 我的课程表接口
 * @date 2022/10/2 14:52
 */
@Api(value = "我的课程表接口", tags = "我的课程表接口")
@Slf4j
@RestController
public class MyCourseTablesController {

    @Autowired
    MyCourseTablesService myCourseTablesService;

    @ApiOperation("添加选课")
    @PostMapping("/choosecourse/{courseId}")
    public XcChooseCourseDto addChooseCourse(@PathVariable("courseId") Long courseId) {
        //登录用户
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        if(user == null){
            XueChengException.cast("请登录后继续选课");
        }
        String userId = user.getId();
        return  myCourseTablesService.addChooseCourse(userId, courseId);

    }
    @ApiOperation("查询学习资格")
    @PostMapping("/choosecourse/learnstatus/{courseId}")
    public XcCourseTablesDto getLearnstatus(@PathVariable("courseId") Long courseId) {
        //登录用户
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        if(user == null){
            XueChengException.cast("请登录后继续选课");
        }
        String userId = user.getId();
        return  myCourseTablesService.getLeanringStatus(userId, courseId);

    }

    @ApiOperation("我的课程表")
    @GetMapping("/mycoursetable")
    public PageResult<XcCourseTables> mycoursetable(MyCourseTableParams params) {
        //登录用户
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        if(user == null){
            XueChengException.cast("请登录后继续选课");
        }
        String userId = user.getId();
        params.setUserId(userId);
        return  myCourseTablesService.mycourestabls(params);
    }
}