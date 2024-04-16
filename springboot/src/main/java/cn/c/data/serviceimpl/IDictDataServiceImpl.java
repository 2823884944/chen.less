package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.DictDataMapper;
import cn.c.data.entity.DictData;
import cn.c.data.service.IDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 数据字典值 服务层实现
 * @author 陈
 */
@Service
public class IDictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

}
