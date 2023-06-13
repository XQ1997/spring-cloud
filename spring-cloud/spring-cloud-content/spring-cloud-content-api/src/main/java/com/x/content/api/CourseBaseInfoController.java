package com.x.content.api;

import com.x.base.exception.ValidationGroups;
import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.base.model.RestResponse;
import com.x.content.model.dto.AddCourseDto;
import com.x.content.model.dto.CourseBaseInfoDto;
import com.x.content.model.dto.EditCourseDto;
import com.x.content.model.dto.QueryCourseParamsDto;
import com.x.content.model.po.CourseBase;
import com.x.content.service.CourseBaseInfoService;
import com.x.content.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author hp
 */
@Api(value = "课程信息编辑接口",tags = "课程信息编辑接口")
@RestController
@RequiredArgsConstructor
public class CourseBaseInfoController {

    private final CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams){
        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);
    }

    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Inster.class) AddCourseDto addCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        return courseBaseInfoService.createCourseBase(22L,addCourseDto);
    }

    @ApiOperation("新增课程基础信息 测试分级校验")
    @PostMapping("/course2")
    @ApiIgnore
    public CourseBaseInfoDto createCourseBase2(@RequestBody @Validated(ValidationGroups.Update.class) AddCourseDto addCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        return courseBaseInfoService.createCourseBase(22L,addCourseDto);
    }

    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        //取出当前登录用户信息
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        System.out.println(user);
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        return courseBaseInfoService.updateCourseBase(1232141425L,editCourseDto);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("del/{courseId}")
    public RestResponse<Boolean> deleteCourseBase(@PathVariable Long courseId) {
        return courseBaseInfoService.deleteCourseBase(courseId);
    }
}
