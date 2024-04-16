package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.UserRoleMapper;
import cn.c.data.entity.UserRole;
import cn.c.data.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-角色关系 服务层实现
 * @author 陈
 */
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
