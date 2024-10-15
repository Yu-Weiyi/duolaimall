package pers.wayease.duolaimall.ware.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.model
 * @name WareOrderTask
 * @description Ware order task class.
 * @since 2024-10-15 20:31
 */
@TableName("ware_order_task")
@Data
public class WareOrderTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField
    private String orderId;
    @TableField
    private String consignee;
    @TableField
    private String consigneeTel;
    @TableField
    private String deliveryAddress;
    @TableField
    private String orderComment;
    @TableField
    private String paymentWay;
    @TableField
    private String taskStatus;
    @TableField
    private String orderBody;
    @TableField
    private String trackingNo;
    @TableField
    private String wareId;
    @TableField
    private String taskComment;

    @TableField(exist = false)
    private List<WareOrderTaskDetail> details;
}
