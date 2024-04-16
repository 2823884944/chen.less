package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.RoleMapper;
import cn.c.data.entity.Role;
import cn.c.data.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色 服务层实现
 * @author 陈 
 */
@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
