package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cardo
 * @Version 1.0
 * @Description TODO
 * @date 2023/5/19 15:06
 */
@Service
@Slf4j
public class CourseCategoryServiceImpl implements CourseCategoryService {
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //找到每个节点的子节点，封装成list
        List<CourseCategoryTreeDto> list = courseCategoryMapper.selectTreeNodes(id);
        //将list转成map，key=id value=CourseCategoryTreeDto,方便从map获取节点   filter(item->!id.equals(item.getId()))排除根节点
        Map<String, CourseCategoryTreeDto> treeDtoMap = list.stream()
                .filter(item->!id.equals(item.getId()))
                .collect(Collectors.toMap(CourseCategory::getId, value->value, (key1, key2) -> key2));

        List<CourseCategoryTreeDto> categoryTreeDtoList =new ArrayList<>();

        //便利list，一边遍历一边找子节点，放在父节点的childrenTreeNodes
        list.forEach(item->{
            if(item.getParentid().equals(id)){
                categoryTreeDtoList.add(item);
            }
            //父节点
            CourseCategoryTreeDto parent = treeDtoMap.get(item.getParentid());
            //找到子节点放在父节点的ChildrenTreeNodes
            if(parent!= null) {
                if (parent.getChildrenTreeNodes() == null) {
                    //如果父节点的ChildrenTreeNodes为空，需要new出来
                    parent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                }
                parent.getChildrenTreeNodes().add(item);
            }
        });
        return categoryTreeDtoList;
    }
}
