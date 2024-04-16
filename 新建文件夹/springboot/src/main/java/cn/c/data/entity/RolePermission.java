package cn.c.data.entity;

import cn.c.basics.baseClass.CsxBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "a_role_permission")
@TableName("a_role_permission")
@ApiModel(value = "角色权限")
public class RolePermission extends CsxBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    private String permissionId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;
}