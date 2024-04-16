package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.SettingMapper;
import cn.c.data.entity.Setting;
import cn.c.data.service.ISettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 设置 服务层实现
 * @author 陈
 */
@Service
public class ISettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

}
