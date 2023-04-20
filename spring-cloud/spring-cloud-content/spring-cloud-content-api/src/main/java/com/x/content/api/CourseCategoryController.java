package com.x.content.api;

import com.x.content.model.dto.CourseCategoryTreeDto;
import com.x.content.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程分类 前端控制器
 * </p>
 *
 * @author itcast
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class CourseCategoryController {
    private final CourseCategoryService courseCategoryService;

    @GetMapping("course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes(){
        return courseCategoryService.queryTreeNodes("1");
    }
}
