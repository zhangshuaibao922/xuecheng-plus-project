package com.xuecheng.content.service;

import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @author cardo
 * @Version 1.0
 * @Description 教师信息相关功能
 * @date 2023/5/22 16:45
 */
public interface CourseTeacherService {
    /**
     * 查询教师信息
     * @param courseId 课程id
     * @return
     */
    public List<CourseTeacher> getCourseTeacherList(Long courseId);

    /**
     * 删除教师信息
     * @param courseId 课程id
     * @param teacherId 教师主键
     */
    void deleteCourseTeacher(Long courseId, Long teacherId);

    /**
     * 修改教师信息接口
     * @param courseTeacher 教师信息
     * @return CourseTeacher
     */
    CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher);

}
