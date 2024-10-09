package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name SkuInfo
 * @description SKU info class.
 * @since 2024-10-09 14:28
 */
@TableName("sku_info")
@Data
public class SkuInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_id")
    private Long spuId;
    @TableField("price")
    private BigDecimal price;
    @TableField("sku_name")
    private String skuName;
    @TableField("sku_desc")
    private String skuDesc;
    @TableField("weight")
    private String weight;
    @TableField("tm_id")
    private Long tmId;
    @TableField("third_level_category_id")
    private Long thirdLevelCategoryId;
    @TableField("sku_default_img")
    private String skuDefaultImg;
    @TableField("is_sale")
    private Integer isSale;

    @TableField(exist = false)
    List<SkuImage> SkuImageList;
    @TableField(exist = false)
    List<SkuPlatformAttributeValue> skuPlatformAttributeValueList;
    @TableField(exist = false)
    List<SkuSaleAttributeValue> skuSaleAttributeValueList;
}
