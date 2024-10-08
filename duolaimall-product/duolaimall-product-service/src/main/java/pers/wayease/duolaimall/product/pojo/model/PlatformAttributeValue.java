package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name PlatformAttributeValue
 * @description Platform attribute value PO class.
 * @since 2024-10-08 10:57
 */
@TableName("platform_attr_value")
@Data
public class PlatformAttributeValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("value_name")
    private String valueName;
    @TableField("attr_id")
    private Long attrId;
}
