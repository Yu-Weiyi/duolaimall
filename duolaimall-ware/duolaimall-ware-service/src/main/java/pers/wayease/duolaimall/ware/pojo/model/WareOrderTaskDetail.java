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
 * @name WareOrderTaskDetail
 * @description Ware order task detail class.
 * @since 2024-10-15 20:32
 */
@TableName("ware_order_task_detail")
@Data
public class WareOrderTaskDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField
    private String skuId;
    @TableField
    private String skuName;
    @TableField
    private Integer skuNum;
    @TableField
    private Long taskId;
}
