package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author cardo
 * @Version 1.0
 * @Description 课程分类相关接口
 * @date 2023/5/19 15:01
 */
public interface CourseCategoryService {
    /**
     * 分类课程树形状结构查询
     * @param id 根节点
     * @return list集合
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
