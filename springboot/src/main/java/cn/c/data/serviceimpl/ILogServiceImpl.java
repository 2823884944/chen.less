package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.LogMapper;
import cn.c.data.entity.Log;
import cn.c.data.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统日志 服务层实现
 * @author 陈
 */
@Service
public class ILogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
