package com.x.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.content.model.dto.CourseCategoryTreeDto;
import com.x.content.model.po.CourseCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
