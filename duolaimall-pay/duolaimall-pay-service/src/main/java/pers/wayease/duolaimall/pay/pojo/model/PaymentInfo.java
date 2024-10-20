package pers.wayease.duolaimall.pay.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.pojo.model
 * @name PaymentInfo
 * @description Payment info class.
 * @since 2024-10-19 03:38
 */
@TableName("payment_info")
@Data
public class PaymentInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("out_trade_no")
    private String outTradeNo;
    @TableField("order_id")
    private Long orderId;
    @TableField("user_id")
    private Long userId;
    @TableField("payment_type")
    private String paymentType;
    @TableField("trade_no")
    private String tradeNo;
    @TableField("total_amount")
    private BigDecimal totalAmount;
    @TableField("subject")
    private String subject;
    @TableField("payment_status")
    private String paymentStatus;
    @TableField("create_time")
    private Date createTime;
    @TableField("callback_time")
    private Date callbackTime;
    @TableField("callback_content")
    private String callbackContent;
}
