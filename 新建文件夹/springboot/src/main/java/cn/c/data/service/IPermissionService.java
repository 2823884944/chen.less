package cn.c.data.service;

import cn.c.data.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单 服务层接口
 * @author 陈
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Permission> findByUserId(String userId);
}
