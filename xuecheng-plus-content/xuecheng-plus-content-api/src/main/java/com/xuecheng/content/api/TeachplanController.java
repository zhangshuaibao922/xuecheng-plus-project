package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cardo
 * @Version 1.0
 * @Description 课程计划编辑接口
 * @date 2023/5/21 15:45
 */
@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachplanController {
    @Autowired
    TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",
            required = true,dataType = "Long",paramType = "path")
    @GetMapping(value = "/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachplanService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){
        teachplanService.saveTeachplan(teachplan);
    }

    @ApiOperation("课程计划删除章节")
    @DeleteMapping("/teachplan/{id}")
    public void deleteChapter(@PathVariable Long id){
        teachplanService.deleteChapter(id);
    }

    @ApiOperation("课程计划排序")
    @PostMapping("/teachplan/{moveType}/{id}")
    public void orderByTeachplan(@PathVariable String moveType,@PathVariable Long id){
        teachplanService.orderByTeachplan(moveType,id);
    }
}
