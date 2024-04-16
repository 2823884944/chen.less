package cn.c.data.dao.mapper;

import cn.c.data.entity.Role;
import cn.c.data.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户-角色关系 数据链路层接口
 * @author 陈
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
