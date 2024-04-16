package cn.c.test.serviceimpl;

import cn.c.test.entity.TeacherDemo;
import cn.c.test.mapper.TeacherDemoMapper;
import cn.c.test.service.ITeacherDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教师 服务层接口实现
 * @author 陈 
 */
@Slf4j
@Service
@Transactional
public class ITeacherDemoServiceImpl extends ServiceImpl<TeacherDemoMapper, TeacherDemo> implements ITeacherDemoService {

    @Autowired
    private TeacherDemoMapper teacherDemoMapper;
}