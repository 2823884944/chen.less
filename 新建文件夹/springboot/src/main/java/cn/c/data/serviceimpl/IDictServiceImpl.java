package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.DictMapper;
import cn.c.data.entity.Dict;
import cn.c.data.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 数据字典 服务层实现
 * @author 陈
 */
@Service
public class IDictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
