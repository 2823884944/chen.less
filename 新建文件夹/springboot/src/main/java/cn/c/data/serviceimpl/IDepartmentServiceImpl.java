package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.DepartmentMapper;
import cn.c.data.entity.Department;
import cn.c.data.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 组织 服务层实现
 * @author 陈
 */
@Service
public class IDepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
