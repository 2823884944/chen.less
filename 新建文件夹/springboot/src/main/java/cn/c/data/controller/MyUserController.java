package cn.c.data.controller;

import cn.c.basics.log.LogType;
import cn.c.basics.log.SystemLog;
import cn.c.basics.utils.PageUtil;
import cn.c.basics.utils.ResultUtil;
import cn.c.basics.baseVo.PageVo;
import cn.c.basics.baseVo.Result;
import cn.c.data.entity.User;
import cn.c.data.service.IUserService;
import cn.c.data.utils.CsxNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 陈
 */
@Slf4j
@RestController
@Api(tags = "mybatis用户接口")
@RequestMapping("/c/myUser")
@Transactional
public class MyUserController {

    @Autowired
    private IUserService iUserService;

    @SystemLog(about = "查询用户", type = LogType.DATA_CENTER,doType = "USER-01")
    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户")
    public Result<IPage<User>> getByPage(@ModelAttribute User user,@ModelAttribute PageVo page){
        QueryWrapper<User> qw = new QueryWrapper<>();
        if(user.getDepartmentId() != null && !CsxNullUtils.isNull(user.getDepartmentId())) {
            qw.like("department_id",user.getDepartmentId());
        }
        if(user.getNickname() != null && !CsxNullUtils.isNull(user.getNickname())) {
            qw.like("nickname",user.getNickname());
        }
        IPage<User> data = iUserService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<User>>().setData(data);
    }
}
