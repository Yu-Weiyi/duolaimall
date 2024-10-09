package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name SpuSaleAttributeInfo
 * @description SPU sale attribute info class.
 * @since 2024-10-08 20:16
 */
@TableName("spu_sale_attr_info")
@Data
public class SpuSaleAttributeInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_id")
    private Long spuId;
    @TableField("sale_attr_id")
    private Long saleAttrId;
    @TableField("sale_attr_name")
    private String saleAttrName;

    @TableField(exist = false)
    List<SpuSaleAttributeValue> spuSaleAttrValueList;

}
