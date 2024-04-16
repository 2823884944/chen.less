package cn.c.stu.serviceimpl;

import cn.c.stu.mapper.StudentEvaluateMapper;
import cn.c.stu.entity.StudentEvaluate;
import cn.c.stu.service.IStudentEvaluateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生评价 服务层接口实现
 * @author 陈
 */
@Slf4j
@Service
@Transactional
public class IStudentEvaluateServiceImpl extends ServiceImpl<StudentEvaluateMapper, StudentEvaluate> implements IStudentEvaluateService {

    @Autowired
    private StudentEvaluateMapper studentEvaluateMapper;
}