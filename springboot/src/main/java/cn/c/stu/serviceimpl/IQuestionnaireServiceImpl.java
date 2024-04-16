package cn.c.stu.serviceimpl;

import cn.c.stu.mapper.QuestionnaireMapper;
import cn.c.stu.entity.Questionnaire;
import cn.c.stu.service.IQuestionnaireService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 问卷 服务层接口实现
 * @author 陈 
 */
@Slf4j
@Service
@Transactional
public class IQuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements IQuestionnaireService {

    @Autowired
    private QuestionnaireMapper questionnaireMapper;
}