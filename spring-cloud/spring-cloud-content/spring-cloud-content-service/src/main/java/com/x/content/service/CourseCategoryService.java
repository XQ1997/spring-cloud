package com.x.content.service;


import com.x.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * <p>
 * 课程分类 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-03-11
 */
public interface CourseCategoryService {

    /**
     *	课程分类树形结构查询
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
