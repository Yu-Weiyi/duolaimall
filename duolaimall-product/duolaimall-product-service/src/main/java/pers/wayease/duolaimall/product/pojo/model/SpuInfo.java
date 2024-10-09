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
 * @name SpuInfo
 * @description SPU Info class.
 * @since 2024-10-08 20:14
 */
@TableName("spu_info")
@Data
public class SpuInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_name")
    private String spuName;
    @TableField("description")
    private String description;
    @TableField("third_level_category_id")
    private Long thirdLevelCategoryId;
    @TableField("tm_id")
    private Long tmId;

    @TableField(exist = false)
    private List<SpuSaleAttributeInfo> spuSaleAttributeInfoList;
    @TableField(exist = false)
    private List<SpuImage> spuImageList;
    @TableField(exist = false)
    private List<SpuPoster> spuPosterList;
}
