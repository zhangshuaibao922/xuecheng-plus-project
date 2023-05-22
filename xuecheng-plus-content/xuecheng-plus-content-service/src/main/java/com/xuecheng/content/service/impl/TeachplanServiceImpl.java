package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cardo
 * @Version 1.0
 * @Description 课程计划service接口实现类
 * @date 2023/5/21 17:01
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        //通过课程计划id判断是新增还是修改
        Long id = teachplanDto.getId();
        if(id!=null){
            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        } else {
            //取出同父同级别的课程计划数量
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            BeanUtils.copyProperties(teachplanDto,teachplanNew);
            teachplanMapper.insert(teachplanNew);

        }
    }

    @Override
    public void deleteChapter(Long id) {
        if(id==null){
            XueChengPlusException.cast("课程计划id为空");
        }
        Teachplan teachplan = teachplanMapper.selectById(id);
        Integer grade = teachplan.getGrade();
        if(grade==1){
            //章
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid,id);
            Integer count = teachplanMapper.selectCount(queryWrapper);
            if(count>0){
                XueChengPlusException.cast("课程计划信息还有子级信息，无法操作");
            }
            //删除
            teachplanMapper.deleteById(id);
        } else {
            //节
            //删除teachplan表
            teachplanMapper.deleteById(id);
            LambdaQueryWrapper<TeachplanMedia> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(TeachplanMedia::getTeachplanId,id);
            //删除TeachplanMedia表
            teachplanMediaMapper.delete(queryWrapper);
            //TODO 删除Teachplanwork表
        }
    }

    @Override
    public void orderByTeachplan(String moveType, Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        //获取当前orderby和grade
        Integer grade = teachplan.getGrade();
        Integer orderby = teachplan.getOrderby();
        //章移动，比较courseId
        Long courseId = teachplan.getCourseId();
        //小姐移动比较同一章节id下的orderBy
        Long parentid = teachplan.getParentid();
        if("moveup".equals(moveType)){
            //上移
            if(grade==1){
                //找到上个章节的orderby交换
//                / SELECT * FROM teachplan WHERE courseId = 117 AND grade = 1
//                AND orderby < 1 ORDER BY orderby DESC LIMIT 1
                LambdaQueryWrapper<Teachplan> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(Teachplan::getCourseId,courseId)
                        .eq(Teachplan::getParentid,0)
                        .lt(Teachplan::getOrderby,orderby)//小于
                        .orderByDesc(Teachplan::getOrderby) //降序
                        .last("LIMIT 1");//限制在一条
                Teachplan tmp = teachplanMapper.selectOne(queryWrapper);
                exchangeOrderBy(teachplan,tmp);
            } else if(grade==2){
                //小节上移
                LambdaQueryWrapper<Teachplan> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(Teachplan::getParentid,parentid)
                        .lt(Teachplan::getOrderby,orderby)
                        .orderByDesc(Teachplan::getOrderby)
                        .last("LIMIT 1");
                Teachplan tmp = teachplanMapper.selectOne(queryWrapper);
                exchangeOrderBy(teachplan,tmp);
            }
        }else if("movedown".equals(moveType)){
            //下移
            if(grade==1){
                //章节下移
                LambdaQueryWrapper<Teachplan> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(Teachplan::getCourseId,courseId)
                        .eq(Teachplan::getParentid,0)
                        .gt(Teachplan::getOrderby,orderby)
                        .orderByAsc(Teachplan::getOrderby)//升序
                        .last("LIMIT 1");
                Teachplan tmp = teachplanMapper.selectOne(queryWrapper);
                exchangeOrderBy(teachplan,tmp);
            } else if (grade == 2) {
                // 小节下移
                // SELECT * FROM teachplan WHERE parentId = 268 AND orderby > 1 ORDER BY orderby ASC LIMIT 1
                LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Teachplan::getParentid, parentid)
                        .gt(Teachplan::getOrderby, orderby)
                        .orderByAsc(Teachplan::getOrderby)
                        .last("LIMIT 1");
                Teachplan tmp = teachplanMapper.selectOne(queryWrapper);
                exchangeOrderBy(teachplan, tmp);
            }
        }
    }

    private void exchangeOrderBy(Teachplan teachplan, Teachplan tmp) {
        if(tmp==null){
            XueChengPlusException.cast("已经到头了，不能再移动了");
        }else {
            // 交换orderby，更新
            Integer orderby = teachplan.getOrderby();
            Integer tmpOrderby = tmp.getOrderby();
            teachplan.setOrderby(tmpOrderby);
            tmp.setOrderby(orderby);
            teachplanMapper.updateById(tmp);
            teachplanMapper.updateById(teachplan);
        }
    }

    /**
     * 获取最新的排序号
     * @param courseId 课程id
     * @param parentId 父课程计划id
     * @return int 最新排序号
     */
    private int getTeachplanCount(long courseId,long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId);
        queryWrapper.eq(Teachplan::getParentid,parentId);
        return teachplanMapper.selectCount(queryWrapper);
    }

}
