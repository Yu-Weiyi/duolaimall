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
 * @name SpuSaleAttributeValue
 * @description SPU sale attribute value class.
 * @since 2024-10-08 20:18
 */
@TableName("spu_sale_attr_value")
@Data
public class SpuSaleAttributeValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_id")
    private Long spuId;
    @TableField("spu_sale_attr_id")
    private Long spuSaleAttrId;
    @TableField("spu_sale_attr_value_name")
    private String spuSaleAttrValueName;

    @TableField(exist = false)
    String isChecked;
}
