package com.x.learning.service;

import com.x.base.model.PageResult;
import com.x.learning.model.dto.MyCourseTableItemDto;
import com.x.learning.model.dto.MyCourseTableParams;
import com.x.learning.model.dto.XcChooseCourseDto;
import com.x.learning.model.dto.XcCourseTablesDto;
import com.x.learning.model.po.XcChooseCourse;
import com.x.learning.model.po.XcCourseTables;

/**
 * @description 我的课程表service接口
 * @author Mr.M
 * @date 2022/10/2 16:07
 * @version 1.0
 */
public interface MyCourseTablesService {

    public XcChooseCourseDto addChooseCourse(String userId, Long courseId);
    /**
     * @description 判断学习资格
     * @param userId
     * @param courseId
     * @return XcCourseTablesDto 学习资格状态 [{"code":"702001","desc":"正常学习"},{"code":"702002","desc":"没有选课或选课后没有支付"},{"code":"702003","desc":"已过期需要申请续期或重新支付"}]
     * @author Mr.M
     * @date 2022/10/3 7:37
     */
    public XcCourseTablesDto getLeanringStatus(String userId, Long courseId);


    public boolean saveChooseCourseStauts(String choosecourseId);

    public PageResult<XcCourseTables> mycourestabls(MyCourseTableParams params);

    /**
     * @description 添加我的课程表
     * @param xcChooseCourse
     * @return com.xuecheng.learning.model.po.XcCourseTables
     * @author Mr.M
     * @date 2022/10/26 11:35
     */
    public XcCourseTables addCourseTabls(XcChooseCourse xcChooseCourse);

}
