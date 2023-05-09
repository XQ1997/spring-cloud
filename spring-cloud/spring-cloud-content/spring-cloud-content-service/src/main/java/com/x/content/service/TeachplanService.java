package com.x.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.x.content.model.dto.BindTeachplanMediaDto;
import com.x.content.model.dto.SaveTeachplanDto;
import com.x.content.model.dto.TeachplanDto;
import com.x.content.model.po.Teachplan;
import com.x.content.model.po.TeachplanMedia;

import java.util.List;

/**
 * <p>
 * 课程计划 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-03-11
 */
public interface TeachplanService extends IService<Teachplan> {

    /**
     *	@description 查询课程计划树型结构
     *	@param courseId 课程id
     *	@return List<TeachplanDto>
     *	@author Mr.M
     * @date 2022/9/9 11:13
     */
    List<TeachplanDto> findTeachplayTree(long courseId);

    /**
     *	@description 只在课程计划
     *	@param teachplanDto 课程计划信息
     *	@author Mr.M
     * @date 2022/9/9 13:39
     */
    void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
     * 教学计划绑定媒资
     *
     * @param bindTeachplanMediaDto 教学计划-媒资管理绑定数据
     * @return {@link com.x.content.model.po.TeachplanMedia}
     * @author Mr.M
     * @since 2022/9/14 22:20
     */
    TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    /**
     * 删除指定教学计划-媒资绑定信息
     *
     * @param teachplanId 教学计划id
     * @param mediaId     媒资id
     */
    void deleteTeachplanMedia(Long teachplanId, String mediaId);
}
