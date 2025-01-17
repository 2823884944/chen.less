package cn.c.test.controller;

import cn.c.basics.log.LogType;
import cn.c.basics.log.SystemLog;
import cn.c.basics.utils.PageUtil;
import cn.c.basics.utils.ResultUtil;
import cn.c.basics.baseVo.PageVo;
import cn.c.basics.baseVo.Result;
import cn.c.data.utils.CsxNullUtils;
import cn.c.data.vo.AntvVo;
import cn.c.test.entity.TeacherDemo;
import cn.c.test.service.ITeacherDemoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 陈
 */
@Slf4j
@RestController
@Api(tags = "教师管理接口")
@RequestMapping("/c/teacherDemo")
@Transactional
public class TeacherDemoController {

    @Autowired
    private ITeacherDemoService iTeacherDemoService;

    @SystemLog(about = "查询单条教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-01")
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条教师")
    public Result<TeacherDemo> get(@RequestParam String id){
        return new ResultUtil<TeacherDemo>().setData(iTeacherDemoService.getById(id));
    }

    @SystemLog(about = "查询全部教师个数", type = LogType.MORE_MOUDLE,doType = "TEACHER-02")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部教师个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iTeacherDemoService.count());
    }

    @SystemLog(about = "查询全部教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-03")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部教师")
    public Result<List<TeacherDemo>> getAll(){
        return new ResultUtil<List<TeacherDemo>>().setData(iTeacherDemoService.list());
    }

    @SystemLog(about = "查询教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-04")
    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询教师")
    public Result<IPage<TeacherDemo>> getByPage(@ModelAttribute TeacherDemo teacher ,@ModelAttribute PageVo page){
        QueryWrapper<TeacherDemo> qw = new QueryWrapper<>();
        if(!CsxNullUtils.isNull(teacher.getName())) {
            qw.like("name",teacher.getName());
        }
        if(!CsxNullUtils.isNull(teacher.getEducation())) {
            qw.eq("education",teacher.getEducation());
        }
        if(!CsxNullUtils.isNull(teacher.getGraduated())) {
            qw.like("graduated",teacher.getGraduated());
        }
        IPage<TeacherDemo> data = iTeacherDemoService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<TeacherDemo>>().setData(data);
    }

    @SystemLog(about = "增改教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-05")
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改教师")
    public Result<TeacherDemo> saveOrUpdate(TeacherDemo teacher){
        if(iTeacherDemoService.saveOrUpdate(teacher)){
            return new ResultUtil<TeacherDemo>().setData(teacher);
        }
        return ResultUtil.error();
    }

    @SystemLog(about = "新增教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-06")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增教师")
    public Result<TeacherDemo> insert(TeacherDemo teacher){
        iTeacherDemoService.saveOrUpdate(teacher);
        return new ResultUtil<TeacherDemo>().setData(teacher);
    }

    @SystemLog(about = "编辑教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-07")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑教师")
    public Result<TeacherDemo> update(TeacherDemo teacher){
        iTeacherDemoService.saveOrUpdate(teacher);
        return new ResultUtil<TeacherDemo>().setData(teacher);
    }

    @SystemLog(about = "删除教师", type = LogType.MORE_MOUDLE,doType = "TEACHER-08")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除教师")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iTeacherDemoService.removeById(id);
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "查询图表数据", type = LogType.CHART,doType = "CHART-01")
    @RequestMapping(value = "/getAntvVoList", method = RequestMethod.GET)
    @ApiOperation(value = "查询图表数据")
    public Result<List<AntvVo>> getAntvVoList(){
        List<AntvVo> ansList = new ArrayList<>();
        List<TeacherDemo> teacherList = iTeacherDemoService.list();
        for (TeacherDemo o : teacherList) {
            boolean flag = false;
            for (AntvVo vo : ansList) {
                if(Objects.equals(vo.getTitle(),o.getName())) {
                    flag = true;
                    vo.setValue(vo.getValue().add(o.getWages()));
                    break;
                }
            }
            if(!flag) {
                AntvVo vo = new AntvVo();
                vo.setTitle(o.getName());
                vo.setType("工资金额");
                vo.setValue(o.getWages());
                ansList.add(vo);
            }
        }
        return new ResultUtil<List<AntvVo>>().setData(ansList);
    }
}
