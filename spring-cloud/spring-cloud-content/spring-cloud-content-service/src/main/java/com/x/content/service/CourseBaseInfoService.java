package com.x.content.service;


import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.base.model.RestResponse;
import com.x.content.model.dto.AddCourseDto;
import com.x.content.model.dto.CourseBaseInfoDto;
import com.x.content.model.dto.EditCourseDto;
import com.x.content.model.dto.QueryCourseParamsDto;
import com.x.content.model.po.CourseBase;

public interface CourseBaseInfoService {
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParams);


    /**添加课程基本信息
     * @param companyId 教学机构id
     * @param addCourseDto 课程基本信息
     * @return CourseBaseInfoDto
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**根据id查询课程信息 包括基本信息和营销信息
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**修改课程信息
     * @param companyId 机构id  要校验本机构只能修改本机构的课程
     * @param editCourseDto 课程信息
     * @return CourseBaseInfoDto
     */
    CourseBaseInfoDto updateCourseBase(long companyId, EditCourseDto editCourseDto);

    /**
     * 删除课程
     * @param courseId 课程 id
     * @return 删除结果
     */
    RestResponse<Boolean> deleteCourseBase(Long courseId);
}
