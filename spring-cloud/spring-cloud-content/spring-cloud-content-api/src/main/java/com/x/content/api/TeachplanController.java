package com.x.content.api;

import com.x.content.model.dto.BindTeachplanMediaDto;
import com.x.content.model.dto.SaveTeachplanDto;
import com.x.content.model.dto.TeachplanDto;
import com.x.content.model.po.TeachplanMedia;
import com.x.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程计划 前端控制器
 * </p>
 *
 * @author itcast
 */
@Api(value = "课程计划管理相关的接口",tags = "课程计划管理相关的接口")
@RequiredArgsConstructor
@Slf4j
@RestController
public class TeachplanController {
    private final TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplayTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("课程计划和媒资信息绑定")
    @PostMapping("/association/media")
    public TeachplanMedia associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto) {
        return teachplanService.associationMedia(bindTeachplanMediaDto);
    }

    @ApiOperation("解绑课程计划和媒资信息")
    @DeleteMapping("/association/media/{teachplanId}/{mediaId}")
    public void dissociationMedia(@PathVariable("teachplanId") Long teachplanId, @PathVariable("mediaId") String mediaId) {
        teachplanService.deleteTeachplanMedia(teachplanId, mediaId);
    }
}