package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.RolePermissionMapper;
import cn.c.data.entity.RolePermission;
import cn.c.data.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色-菜单关系 服务层实现
 * @author 陈
 */
@Service
public class IRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}
