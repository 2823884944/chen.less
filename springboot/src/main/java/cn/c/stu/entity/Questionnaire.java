package cn.c.stu.entity;

import cn.c.basics.baseClass.CsxBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 陈
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_questionnaire")
@TableName("a_questionnaire")
@ApiModel(value = "问卷")
public class Questionnaire extends CsxBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评分ID")
    private String evaluateId;

    @ApiModelProperty(value = "问卷类型",notes = "1学业发展水平自评 | 2品德与社会化水平自评 | 3品德与社会化水平教师评 | 4身心健康水平自评 | 5身心健康水平教师评")
    private int type;

    @ApiModelProperty(value = "问卷内容")
    private String value;

    @ApiModelProperty(value = "是否提交",notes = "0 未提交 | 1 已提交")
    private int status;
}