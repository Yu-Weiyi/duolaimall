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
 * @name SkuSaleAttributeValue
 * @description SKU sale attribute value class.
 * @since 2024-10-09 14:33
 */
@TableName("sku_sale_attr_value")
@Data
public class SkuSaleAttributeValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("sku_id")
    private Long skuId;
    @TableField("spu_id")
    private Long spuId;
    @TableField("spu_sale_attr_value_id")
    private Long spuSaleAttrValueId;
}
