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
 * @name ThirdLevelCategory
 * @description Third level category class.
 * @since 2024-10-06 21:06
 */
@TableName("third_level_category")
@Data
public class ThirdLevelCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;
    @TableField("second_level_category_id")
    private Long secondLevelCategoryId;
}
