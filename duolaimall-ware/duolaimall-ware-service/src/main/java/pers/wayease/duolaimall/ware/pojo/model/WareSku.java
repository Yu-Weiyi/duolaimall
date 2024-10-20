package pers.wayease.duolaimall.ware.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.model
 * @name WareSku
 * @description Ware SKU class.
 * @since 2024-10-15 20:33
 */
@TableName("ware_sku")
@Data
public class WareSku extends BaseEntity {

    private static final long serialVersionUID = 1L;

    // TODO rewrite BaseEntity
    // conflict with BaseEntity
//    // use snowflake algorithm generate ID
//    @TableId(type = IdType.ASSIGN_ID)
//    private  String id;

    @TableField
    private String skuId;
    @TableField
    private String warehouseId;
    @TableField
    private Integer stock=0;
    @TableField
    private  String stockName;
    @TableField
    private Integer stockLocked;

    @TableField(exist = false)
    private  String warehouseName;
}
