package com.x.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.content.model.dto.TeachplanDto;
import com.x.content.model.po.Teachplan;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    /**
     *	@description 查询某课程的课程计划，组成树型结构
     *	@param courseId 课程id
     *	@return com.xc.content.model.dto.TeachplanDto
     *	@author Mr.M
     * @date 2022/9/9 11:10
     */
    List<TeachplanDto> selectTreeNodes(Long courseId);
}
