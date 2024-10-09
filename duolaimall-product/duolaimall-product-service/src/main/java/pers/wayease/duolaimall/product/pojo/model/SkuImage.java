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
 * @name SkuImage
 * @description SKU image class.
 * @since 2024-10-09 14:29
 */
@TableName("sku_image")
@Data
public class SkuImage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("sku_id")
    private Long skuId;
    @TableField(exist = false)
    private String imgName;
    @TableField(exist = false)
    private String imgUrl;
    @TableField("spu_img_id")
    private Long spuImgId;
    @TableField("is_default")
    private String isDefault;
}
