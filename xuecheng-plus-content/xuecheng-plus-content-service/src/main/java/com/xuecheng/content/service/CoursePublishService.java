package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CoursePreviewDto;

/**
 * @author cardo
 * @Version 1.0
 * @Description  课程预览、发布接口
 * @date 2023/5/28 17:05
 */
public interface CoursePublishService {
    /**
     * 根据课程id获取课程预览信息
     * @param courseId  课程id
     * @return CoursePreviewDto
     */
    CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * 提交审核
     * @param companyId 机构id
     * @param courseId 课程id
     */

    void commitAudit(Long companyId,Long courseId);

    /**
     * 课程发布
     * @param companyId 机构id
     * @param courseId 课程id
     */
    void publish(Long companyId, Long courseId);
}
