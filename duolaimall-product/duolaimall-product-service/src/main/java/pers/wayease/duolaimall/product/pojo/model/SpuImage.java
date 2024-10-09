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
 * @name SpuImage
 * @description SPU image class.
 * @since 2024-10-08 20:22
 */
@TableName("spu_image")
@Data
public class SpuImage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_id")
    private Long spuId;
    @TableField("img_name")
    private String imgName;
    @TableField("img_url")
    private String imgUrl;
}
