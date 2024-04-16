package cn.c.data.serviceimpl;

import cn.c.data.dao.mapper.FileMapper;
import cn.c.data.entity.File;
import cn.c.data.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统文件 服务层实现
 * @author 陈
 */
@Service
public class IFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
