package cn.c.data.entity;

import cn.c.basics.baseClass.CsxBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "a_setting")
@TableName("a_setting")
@ApiModel(value = "配置")
@NoArgsConstructor
public class Setting extends CsxBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设置内容")
    private String value;

    public Setting(String id){
        super.setId(id);
    }
}