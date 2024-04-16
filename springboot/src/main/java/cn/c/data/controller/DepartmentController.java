package cn.c.data.controller;

import cn.c.basics.log.LogType;
import cn.c.basics.log.SystemLog;
import cn.c.basics.parameter.CommonConstant;
import cn.c.basics.exception.CsxException;
import cn.c.basics.redis.RedisTemplateHelper;
import cn.c.basics.utils.CommonUtil;
import cn.c.basics.utils.HibernateProxyTypeAdapter;
import cn.c.basics.utils.ResultUtil;
import cn.c.basics.utils.SecurityUtil;
import cn.c.basics.baseVo.Result;
import cn.c.data.entity.Department;
import cn.c.data.entity.DepartmentHeader;
import cn.c.data.entity.User;
import cn.c.data.service.IDepartmentHeaderService;
import cn.c.data.service.IDepartmentService;
import cn.c.data.service.IUserService;
import cn.c.data.utils.CsxNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 组织管理
 * @author 陈
 */
@RestController
@Api(tags = "组织管理接口")
@RequestMapping("/c/department")
@CacheConfig(cacheNames = "department")
@Transactional
public class DepartmentController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTemplateHelper redisTemplateHelper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDepartmentHeaderService iDepartmentHeaderService;

    private static final String ONE_LEVEL_PARENT_TITLE = "一级组织";

    private static final String REDIS_DEPARTMENT_PRE_STR = "department::";

    private static final String REDIS_STEP_STR = ":";

    @SystemLog(about = "查询子组织", type = LogType.DATA_CENTER,doType = "DEP-01")
    @RequestMapping(value = "/getByParentId", method = RequestMethod.GET)
    @ApiOperation(value = "查询子组织")
    public Result<List<Department>> getByParentId(@RequestParam(required = false,defaultValue = "0") String parentId){
        List<Department> list = null;
        User nowUser = securityUtil.getCurrUser();
        String key = REDIS_DEPARTMENT_PRE_STR + parentId + REDIS_STEP_STR + nowUser.getId();
        String value = redisTemplate.opsForValue().get(key);
        if(!CsxNullUtils.isNull(value)){
            list = new Gson().fromJson(value, new TypeToken<List<Department>>(){}.getType());
            return new ResultUtil<List<Department>>().setData(list);
        }
        QueryWrapper<Department> depQw = new QueryWrapper<>();
        depQw.eq("parent_id",parentId);
        depQw.orderByAsc("sort_order");
        list = iDepartmentService.list(depQw);
        list = setInfo(list);
        redisTemplate.opsForValue().set(key,new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<Department>>().setData(list);
    }

    @SystemLog(about = "模糊搜索组织", type = LogType.DATA_CENTER,doType = "DEP-02")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "模糊搜索组织")
    public Result<List<Department>> search(@RequestParam String title){
        QueryWrapper<Department> depQw = new QueryWrapper<>();
        depQw.like("title",title);
        depQw.orderByDesc("sort_order");
        List<Department> departmentList = iDepartmentService.list(depQw);
        return new ResultUtil<List<Department>>().setData(setInfo(departmentList));
    }

    @SystemLog(about = "添加组织", type = LogType.DATA_CENTER,doType = "DEP-03")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加组织")
    public Result<Object> add(Department department){
        iDepartmentService.saveOrUpdate(department);
        if(!Objects.equals(CommonConstant.PARENT_ID,department.getParentId())){
            Department parentDepartment = iDepartmentService.getById(department.getParentId());
            if(parentDepartment.getIsParent() == null|| !parentDepartment.getIsParent()){
                parentDepartment.setIsParent(true);
                iDepartmentService.saveOrUpdate(parentDepartment);
            }
        }
        Set<String> keyListInSet = redisTemplateHelper.keys(REDIS_DEPARTMENT_PRE_STR + "*");
        redisTemplate.delete(keyListInSet);
        return ResultUtil.success();
    }

    @SystemLog(about = "编辑组织", type = LogType.DATA_CENTER,doType = "DEP-04")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑组织")
    public Result<Object> edit(Department department,@RequestParam(required = false) String[] mainHeader,@RequestParam(required = false) String[] viceHeader){
        Department oldDepartment = iDepartmentService.getById(department.getId());
        iDepartmentService.saveOrUpdate(department);
        QueryWrapper<DepartmentHeader> dhQw = new QueryWrapper<>();
        dhQw.eq("department_id",department.getId());
        iDepartmentHeaderService.remove(dhQw);
        List<DepartmentHeader> departmentHeaderList = new ArrayList<>();
        if(mainHeader != null){
            for(String mainHeaderId : mainHeader){
                DepartmentHeader dh = new DepartmentHeader().setUserId(mainHeaderId).setDepartmentId(department.getId()).setType(0);
                departmentHeaderList.add(dh);
            }
        }
        if(viceHeader != null){
            for(String viceHeaderId : viceHeader){
                DepartmentHeader dh = new DepartmentHeader().setUserId(viceHeaderId).setDepartmentId(department.getId()).setType(1);
                departmentHeaderList.add(dh);
            }
        }
        iDepartmentHeaderService.saveOrUpdateBatch(departmentHeaderList);
        if(!oldDepartment.getTitle().equals(department.getTitle())){
            QueryWrapper<User> userQw = new QueryWrapper<>();
            userQw.eq("department_id",department.getId());
            List<User> userList = iUserService.list(userQw);
            for (User user : userList) {
                user.setDepartmentTitle(department.getTitle());
                iUserService.saveOrUpdate(user);
            }
            Set<String> keysUser = redisTemplateHelper.keys("user:" + "*");
            redisTemplate.delete(keysUser);
        }
        Set<String> keys = redisTemplateHelper.keys("department:" + "*");
        if(keys != null) {
            redisTemplate.delete(keys);
        }
        return ResultUtil.success();
    }

    @SystemLog(about = "删除组织", type = LogType.DATA_CENTER,doType = "DEP-05")
    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除组织")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String departmentId : ids){
            deleteRecursion(departmentId, ids);
        }
        Set<String> keyListInSet = redisTemplateHelper.keys("department:" + "*");
        if(keyListInSet != null) {
            redisTemplate.delete(keyListInSet);
        }
        Set<String> keysUserRoleData = redisTemplateHelper.keys("userRole::depIds:" + "*");
        redisTemplate.delete(keysUserRoleData);
        return ResultUtil.success();
    }

    @ApiOperation(value = "迭代删除组织")
    public void deleteRecursion(String id, String[] ids) {
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("department_id",id);
        long userCountInDepartment = iUserService.count(userQw);
        if(userCountInDepartment > 0L){
            throw new CsxException("不能删除包含员工的组织");
        }
        Department department = iDepartmentService.getById(id);
        Department parentDepartment = null;
        if(department != null && !CsxNullUtils.isNull(department.getParentId())){
            parentDepartment = iDepartmentService.getById(department.getParentId());
        }
        iDepartmentService.removeById(id);
        QueryWrapper<DepartmentHeader> dhQw = new QueryWrapper<>();
        dhQw.eq("department_id",id);
        iDepartmentHeaderService.remove(dhQw);
        if(parentDepartment != null){
            QueryWrapper<Department> depQw = new QueryWrapper<>();
            depQw.eq("parent_id",parentDepartment.getId());
            depQw.orderByAsc("sort_order");
            List<Department> childrenDepartmentList = iDepartmentService.list(depQw);
            if(childrenDepartmentList == null || childrenDepartmentList.size() < 1){
                parentDepartment.setIsParent(false);
                iDepartmentService.saveOrUpdate(parentDepartment);
            }
        }
        QueryWrapper<Department> depQw = new QueryWrapper<>();
        depQw.eq("parent_id",id);
        depQw.orderByAsc("sort_order");
        List<Department> departmentList = iDepartmentService.list(depQw);
        for(Department judgeDepartment : departmentList){
            if(!CommonUtil.judgeIds(judgeDepartment.getId(), ids)){
                deleteRecursion(judgeDepartment.getId(), ids);
            }
        }
    }

    @ApiOperation(value = "增加一级组织标识")
    public List<Department> setInfo(List<Department> list) {
        list.forEach(item -> {
            if(!Objects.equals(CommonConstant.PARENT_ID,item.getParentId())){
                Department parentDepartment = iDepartmentService.getById(item.getParentId());
                if(parentDepartment == null) {
                    item.setParentTitle("无");
                } else {
                    item.setParentTitle(parentDepartment.getTitle());
                }
            }else{
                item.setParentTitle(ONE_LEVEL_PARENT_TITLE);
            }
            QueryWrapper<DepartmentHeader> dh1 = new QueryWrapper<>();
            dh1.eq("department_id",item.getId());
            dh1.eq("type",0);
            List<DepartmentHeader> headerList1 = iDepartmentHeaderService.list(dh1);
            List<String> mainHeaderList = new ArrayList<>();
            for (DepartmentHeader dh : headerList1) {
                mainHeaderList.add(dh.getUserId());
            }
            item.setMainHeader(mainHeaderList);

            QueryWrapper<DepartmentHeader> dh2 = new QueryWrapper<>();
            dh2.eq("department_id",item.getId());
            dh2.eq("type",1);
            List<DepartmentHeader> headerList2 = iDepartmentHeaderService.list(dh2);
            List<String> viceHeaderList = new ArrayList<>();
            for (DepartmentHeader dh : headerList2) {
                viceHeaderList.add(dh.getUserId());
            }
            item.setViceHeader(viceHeaderList);
        });
        return list;
    }

    @ApiOperation(value = "添加模拟搜索标志")
    private String addLikeStr(String str) {
        return "%" + str + "%";
    }
}
