package com.xuecheng;

import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
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
    TeachplanMapper teachplanMapper;
    @Test
    public void testCourseBaseMap(){
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(117L);
        System.out.println(teachplanDtos);
    }
}
