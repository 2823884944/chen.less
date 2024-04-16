package cn.c.data.controller;

import cn.c.basics.log.LogType;
import cn.c.basics.log.SystemLog;
import cn.c.basics.utils.PageUtil;
import cn.c.basics.utils.ResultUtil;
import cn.c.basics.baseVo.PageVo;
import cn.c.basics.baseVo.Result;
import cn.c.data.entity.Log;
import cn.c.data.service.ILogService;
import cn.c.data.utils.CsxNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志
 * @author 陈 
 */
@RestController
@Api(tags = "日志管理接口")
@RequestMapping("/c/log")
@Transactional
public class LogController{

    @Autowired
    private ILogService iLogService;

    @SystemLog(about = "查询日志", type = LogType.DATA_CENTER,doType = "LOG-01")
    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询日志")
    public Result<Object> getAllByPage(@ModelAttribute Log log, @ModelAttribute PageVo page){
        QueryWrapper<Log> qw = new QueryWrapper<>();
        if(!CsxNullUtils.isNull(log.getName())) {
            qw.like("name",log.getName());
        }
        if(log.getLogType() != null) {
            qw.eq("log_type",log.getLogType());
        }
        if(!CsxNullUtils.isNull(log.getUsername())) {
            qw.like("username",log.getUsername());
        }
        if(!CsxNullUtils.isNull(log.getIp())) {
            qw.like("ip",log.getIp());
        }
        if(!CsxNullUtils.isNull(log.getStartDate())) {
            qw.ge("create_time",log.getStartDate());
            qw.le("create_time",log.getEndDate());
        }
        return ResultUtil.data(iLogService.page(PageUtil.initMpPage(page),qw));
    }
}
