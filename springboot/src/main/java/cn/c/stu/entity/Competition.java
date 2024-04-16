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
@Table(name = "a_competition")
@TableName("a_competition")
@ApiModel(value = "学科竞赛")
public class Competition extends CsxBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评分ID")
    private String evaluateId;

    @ApiModelProperty(value = "竞赛级别")
    private String level;

    @ApiModelProperty(value = "竞赛名称")
    private String title;

    @ApiModelProperty(value = "竞赛奖项")
    private String value;
}