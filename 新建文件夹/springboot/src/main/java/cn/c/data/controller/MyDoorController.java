package cn.c.data.controller;

import cn.c.basics.baseVo.Result;
import cn.c.basics.log.LogType;
import cn.c.basics.log.SystemLog;
import cn.c.basics.utils.ResultUtil;
import cn.c.basics.utils.SecurityUtil;
import cn.c.data.entity.Permission;
import cn.c.data.entity.User;
import cn.c.data.service.IPermissionService;
import cn.c.data.service.IUserService;
import cn.c.data.utils.CsxNullUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 陈
 */
@RestController
@Api(tags = "个人信息接口")
@RequestMapping("/c/myDoor")
@Transactional
public class MyDoorController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private IUserService iUserService;

    @SystemLog(about = "查询个人信息菜单A", type = LogType.DATA_CENTER,doType = "MY-DOOR-01")
    @ApiOperation(value = "查询个人信息菜单A")
    @RequestMapping(value = "/getMyDoorList", method = RequestMethod.POST)
    public Result<List<MyDoorMenuClass>> getMyDoorList(){
        User user = securityUtil.getCurrUser();
        user = iUserService.getById(user.getId());
        List<MyDoorMenuClass> ans = new ArrayList<>();
        String myDoor = user.getMyDoor();
        if(CsxNullUtils.isNull(myDoor)) {
            return new ResultUtil().setData(ans);
        }
        String[] C666s = myDoor.split("C666");
        List<Permission> all = iPermissionService.list();
        for (String C666 : C666s) {
            for (Permission permission : all) {
                if(Objects.equals(permission.getName(),C666)) {
                    MyDoorMenuClass menu = new MyDoorMenuClass();
                    menu.setName(permission.getName());
                    menu.setTitle(permission.getTitle());
                    ans.add(menu);
                    break;
                }
            }
        }
        return new ResultUtil().setData(ans);
    }

    @SystemLog(about = "查询个人信息菜单B", type = LogType.DATA_CENTER,doType = "MY-DOOR-02")
    @ApiOperation(value = "获取个人信息菜单B")
    @RequestMapping(value = "/getMyDoorList6", method = RequestMethod.POST)
    public Result<List<MyDoorMenuClass>> getMyDoorList6(){
        User user = securityUtil.getCurrUser();
        user = iUserService.getById(user.getId());
        List<MyDoorMenuClass> ans = new ArrayList<>();
        String myDoor = user.getMyDoor();
        if(CsxNullUtils.isNull(myDoor)) {
            ans.add(getNullMenu());ans.add(getNullMenu());ans.add(getNullMenu());
            ans.add(getNullMenu());ans.add(getNullMenu());ans.add(getNullMenu());
            return new ResultUtil().setData(ans);
        }
        String[] C666s = myDoor.split("C666");
        List<Permission> all = iPermissionService.list();
        for (String C666 : C666s) {
            for (Permission permission : all) {
                if(Objects.equals(permission.getName(),C666)) {
                    MyDoorMenuClass menu = new MyDoorMenuClass();
                    menu.setName(permission.getName());
                    menu.setTitle(permission.getTitle());
                    ans.add(menu);
                    break;
                }
            }
        }
        int size = ans.size();
        if(size < 6) {
            int time = 6 - size;
            for(int i = 0 ; i < time; i ++) {
                ans.add(getNullMenu());
            }
        }
        return new ResultUtil().setData(ans);
    }

    @SystemLog(about = "修改个人信息菜单", type = LogType.DATA_CENTER,doType = "MY-DOOR-03")
    @ApiOperation(value = "修改个人信息菜单")
    @RequestMapping(value = "/setMyDoorList", method = RequestMethod.POST)
    public Result<Object> setMyDoorList(@RequestParam String str){
        User user = securityUtil.getCurrUser();
        user = iUserService.getById(user.getId());
        if(user != null) {
            if(CsxNullUtils.isNull(str)) {
                user.setMyDoor("");
                iUserService.saveOrUpdate(user);
            } else {
                user.setMyDoor(str);
                iUserService.saveOrUpdate(user);
            }
            return ResultUtil.success("OK");
        }
        return ResultUtil.error("ROSTER IS NULL");
    }

    private MyDoorMenuClass getNullMenu() {
        MyDoorMenuClass menu = new MyDoorMenuClass();
        menu.setName("null");
        menu.setTitle("尚未添加");
        return menu;
    }

    @Data
    private class MyDoorMenuClass {
        private String name;
        private String title;
    }
}
