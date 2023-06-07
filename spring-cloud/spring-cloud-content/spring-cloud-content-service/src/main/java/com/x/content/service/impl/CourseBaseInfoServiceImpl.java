package com.x.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.x.base.exception.XueChengException;
import com.x.base.model.PageParams;
import com.x.base.model.PageResult;
import com.x.base.model.RestResponse;
import com.x.content.mapper.CourseBaseMapper;
import com.x.content.mapper.CourseCategoryMapper;
import com.x.content.mapper.CourseMarketMapper;
import com.x.content.model.dto.AddCourseDto;
import com.x.content.model.dto.CourseBaseInfoDto;
import com.x.content.model.dto.EditCourseDto;
import com.x.content.model.dto.QueryCourseParamsDto;
import com.x.content.model.po.CourseBase;
import com.x.content.model.po.CourseCategory;
import com.x.content.model.po.CourseMarket;
import com.x.content.service.CourseBaseInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    private final CourseBaseMapper courseBaseMapper;
    private final CourseMarketMapper courseMarketMapper;
    private final CourseCategoryMapper courseCategoryMapper;
    private final CourseMarketServiceImpl courseMarketService;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //构建查询条件，根据课程名称查询

        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程审核状态查询

        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程发布状态查询

        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getAuditStatus,queryCourseParamsDto.getPublishStatus());

        //分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        return new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto,courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //设置发布状态
        courseBaseNew.setStatus("203001");
        //机构id
        courseBaseNew.setCompanyId(companyId);
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);
        Long courseId = courseBaseNew.getId();
        //课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarketNew);
        courseMarketNew.setId(courseId);
        int insert1 = this.saveCourseMarket(courseMarketNew);

        if (insert < 1 || insert1 < 1) {
            XueChengException.cast("添加课程失败");
        }
        //返回添加的课程信息
        return getCourseBaseInfo(courseId);
    }

    public CourseBaseInfoDto getCourseBaseInfo(Long courseId){
        //课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        //课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        //组成要返回的数据
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket!=null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //向分类的名称查询出来
        CourseCategory courseCategory = courseCategoryMapper.selectById(courseBase.getMt());//一级分类
        courseBaseInfoDto.setMtName(courseCategory.getName());
        CourseCategory courseCategory2 = courseCategoryMapper.selectById(courseBase.getSt());//二级分类
        courseBaseInfoDto.setStName(courseCategory2.getName());

        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto updateCourseBase(long companyId, EditCourseDto editCourseDto) {
        // 校验
        Long courseId = editCourseDto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengException.cast("课程不存在");
        }
        // 校验本机构只能修改本机构的课程
        if (!courseBase.getCompanyId().equals(companyId)) {
            XueChengException.cast("本机构只能修改本机构的课程");
        }
        // 封装基本信息的数据
        BeanUtils.copyProperties(editCourseDto, courseBase);

        // 更新课程基本信息
        courseBase.setChangeDate(LocalDateTime.now());
        courseBaseMapper.updateById(courseBase);

        // 封装营销信息的数据
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if (courseMarket == null) {
            courseMarket = new CourseMarket();
        }
        BeanUtils.copyProperties(editCourseDto, courseMarket);
        int update = this.saveCourseMarket(courseMarket);
        System.out.println("更新营销表影响条数：" + update);
        // 查询课程信息并返回
        return getCourseBaseInfo(courseId);
    }

    @Override
    public RestResponse<Boolean> deleteCourseBase(Long courseId) {
        int delete = courseBaseMapper.deleteById(courseId);
        if (delete > 0) {
            return RestResponse.success(true);
        }
        return RestResponse.success(false);
    }

    /**
     * 抽取营销信息的保存
     * @param courseMarket 课程营销对象
     * @return 返回更新条数
     */
    private int saveCourseMarket(CourseMarket courseMarket) {
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)) {
            XueChengException.cast("收费规则没有选择");
        }
        // 如果是收费课程，价格必须输入  收费
        if ("201001".equals(charge)) {
            Float price = courseMarket.getPrice();
            if (price == null || price <= 0) {
                XueChengException.cast("课程设置了收费价格不能为空且必须大于0");
            }
        }
        // 保存或更新
        return courseMarketService.saveOrUpdate(courseMarket) ? 1 : 0;
    }
}
