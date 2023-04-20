package com.x.content.model.dto;

import com.x.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *	@author hp
 * @description 课程分类树型结点dto
 *	@author Mr.M
 * @date 2022/9/7 15:16
 *	@version 1.0
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    List childrenTreeNodes;
}
