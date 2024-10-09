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
 * @name SpuPoster
 * @description SPU poster class.
 * @since 2024-10-08 20:23
 */
@TableName("spu_poster")
@Data
public class SpuPoster extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_id")
    private Long spuId;
    @TableField("img_name")
    private String imgName;
    @TableField("img_url")
    private String imgUrl;
}
