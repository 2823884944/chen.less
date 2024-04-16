package cn.c.data.entity;

import cn.c.basics.baseClass.CsxBaseEntity;
import cn.c.basics.parameter.CommonConstant;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 陈 
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_department_header")
@TableName("a_department_header")
@ApiModel(value = "组织负责人")
public class DepartmentHeader extends CsxBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "组织ID")
    private String departmentId;

    @ApiModelProperty(value = "领导类型")
    private Integer type = 0;
}