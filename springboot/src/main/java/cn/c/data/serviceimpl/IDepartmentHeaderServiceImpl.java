package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.DepartmentHeaderMapper;
import cn.c.data.entity.DepartmentHeader;
import cn.c.data.service.IDepartmentHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 组织领导 服务层实现
 * @author 陈 
 */
@Service
public class IDepartmentHeaderServiceImpl extends ServiceImpl<DepartmentHeaderMapper, DepartmentHeader> implements IDepartmentHeaderService {

}
