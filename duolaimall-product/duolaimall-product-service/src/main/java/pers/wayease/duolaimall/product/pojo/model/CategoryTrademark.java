package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name CategoryTrademark
 * @description Category trademark class.
 * @since 2024-10-08 19:35
 */
@TableName("category_trademark")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTrademark extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("third_level_category_id")
    private Long thirdLevelCategoryId;
    @TableField("trademark_id")
    private Long trademarkId;

    @TableField(exist = false)
    private Trademark trademark;
}
