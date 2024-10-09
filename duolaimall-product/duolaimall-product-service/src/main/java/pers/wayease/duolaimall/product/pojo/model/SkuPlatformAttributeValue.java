package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name SkuPlatformAttributeValue
 * @description SKU platform attribute value class.
 * @since 2024-10-09 14:31
 */
@TableName("sku_platform_attr_value")
@Data
public class SkuPlatformAttributeValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("attr_id")
    private Long attrId;
    @TableField("value_id")
    private Long valueId;
    @TableField("sku_id")
    private Long skuId;
}
