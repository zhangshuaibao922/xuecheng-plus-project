package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cardo
 * @Version 1.0
 * @Description 修改课程基本信息dto
 * @date 2023/5/20 15:29
 */
@ApiModel(value = "EditCourseDto",description = "修改课程基本信息dto")
@Data
public class EditCourseDto extends AddCourseDto{
    @ApiModelProperty(value = "课程id" ,required = true)
    private Long id;

}
