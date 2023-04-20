package com.x.content.service.impl;

import com.x.content.mapper.CourseCategoryMapper;
import com.x.content.model.dto.CourseCategoryTreeDto;
import com.x.content.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author itcast
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    private final CourseCategoryMapper courseCategoryMapper;

    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //查询数据库得到的课程分类
        List<CourseCategoryTreeDto> courseCategoryTrees = courseCategoryMapper.selectTreeNodes(id);
        //最终返回的列表
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>(); HashMap<String, CourseCategoryTreeDto> mapTemp = new HashMap<>();

        courseCategoryTrees.stream().forEach(item->{ mapTemp.put(item.getId(),item);
            //只将根节点的下级节点放入list
            if(item.getParentid().equals(id)){ categoryTreeDtos.add(item);
            }
            CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());
            if(courseCategoryTreeDto!=null){
                if(courseCategoryTreeDto.getChildrenTreeNodes() ==null){
                    courseCategoryTreeDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                //向节点的下级节点list加入节点
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }
        });
        return categoryTreeDtos;
    }

}
