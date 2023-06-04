package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author cardo
 * @Version 1.0
 * @Description 课程基本信息管理业务接口
 * @date 2023/5/21 16:58
 */
public interface TeachplanService {
    /**
     * 查询课程计划树形结构
     * @param courseId 课程id
     * @return List<TeachplanDto>
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * 新建课程计划
     * @param teachplanDto
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
     * 删除课程计划
     * @param id 主键id
     * @return ResultVo
     */
    public void deleteChapter(Long id);

    /**
     * 课程计划排序
     * @param moveType 排序类别
     * @param id 主键id
     */
    public void orderByTeachplan(String moveType,Long id);

    /**
     * 教学计划绑定媒资信息
     * @param bindTeachplanMediaDto
     */
    void associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    /** 解绑教学计划与媒资信息
     * @param teachPlanId       教学计划id
     * @param mediaId           媒资信息id
     */
    void unassociationMedia(Long teachPlanId, Long mediaId);
}
