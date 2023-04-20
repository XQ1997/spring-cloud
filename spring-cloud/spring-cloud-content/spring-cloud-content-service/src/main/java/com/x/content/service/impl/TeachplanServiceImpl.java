package com.x.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.base.exception.XueChengException;
import com.x.content.mapper.TeachplanMapper;
import com.x.content.mapper.TeachplanMediaMapper;
import com.x.content.model.dto.BindTeachplanMediaDto;
import com.x.content.model.dto.SaveTeachplanDto;
import com.x.content.model.dto.TeachplanDto;
import com.x.content.model.po.Teachplan;
import com.x.content.model.po.TeachplanMedia;
import com.x.content.service.TeachplanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 课程计划 服务实现类
 * </p>
 *
 * @author itcast
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {
    private final TeachplanMapper  teachplanMapper;
    private final TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplayTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        Long id = teachplanDto.getId();
        //修改课程计划
        if (id != null) {
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplan.setChangeDate(LocalDateTime.now());
            teachplanMapper.updateById(teachplan);
        } else {
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            //添加时间
            teachplanNew.setCreateDate(LocalDateTime.now());
            BeanUtils.copyProperties(teachplanDto, teachplanNew);
            int f = teachplanMapper.insert(teachplanNew);
            System.out.println(f);
            System.out.println(teachplanNew);
        }
    }

    /**
     *	@description 获取最新的排序号
     *	@param courseId 课程id
     *	@param parentId 父课程计划id
     *	@return int 最新排序号
     *	@author Mr.M
     * @date 2022/9/9 13:43
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId); queryWrapper.eq(Teachplan::getParentid,parentId);
        return teachplanMapper.selectCount(queryWrapper);
    }

    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        // 获取教学计划 id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        // 查询教学计划
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if (teachplan == null) {
            XueChengException.cast(String.format("教学计划 %s 不存在", teachplanId));
        }
        // 得到层级
        Integer grade = teachplan.getGrade();
        if (grade != 2) {
            XueChengException.cast("只允许第二级教学计划绑定媒资文件");
        }
        // 课程 id
        Long courseId = teachplan.getCourseId();
        // 先删除原来教学计划绑定的媒资
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId, teachplanId));

        // 再添加教学计划与媒资的关系
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        // 插入数据库
        int insert = teachplanMediaMapper.insert(teachplanMedia);
        if (insert <= 0) {
            XueChengException.cast("教学计划-媒资表插入数据失败");
        }
        return teachplanMedia;
    }

    @Override
    public void deleteTeachplanMedia(Long teachplanId, String mediaId) {
        int delete = teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>()
                .eq(TeachplanMedia::getTeachplanId, teachplanId)
                .eq(TeachplanMedia::getMediaId, mediaId));
        if (delete <= 0) {
            log.warn("删除课程计划{}-媒资{}信息失败", teachplanId, mediaId);
        }
    }
}
