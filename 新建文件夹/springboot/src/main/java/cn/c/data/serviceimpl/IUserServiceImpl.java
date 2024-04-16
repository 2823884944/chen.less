package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.UserMapper;
import cn.c.data.entity.User;
import cn.c.data.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务层实现
 * @author 陈 
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
