package com.xuecheng;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author cardo
 * @Version 1.0
 * @Description TODO
 * @date 2023/5/18 18:13
 */
@SpringBootTest
public class CourseBaseMapperTests {
    @Autowired
    CourseCategoryService service;
    @Test
    public void testCourseBaseMap(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = service.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
