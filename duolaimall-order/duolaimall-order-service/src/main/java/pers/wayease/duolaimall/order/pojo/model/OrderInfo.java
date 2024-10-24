package pers.wayease.duolaimall.order.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;
import pers.wayease.duolaimall.order.pojo.constant.OrderTypeEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.model
 * @name OrderInfo
 * @description Order info class.
 * @since 2024-10-15 21:34
 */
@TableName("order_info")
@Data
public class OrderInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("parent_order_id")
    private Long parentOrderId;
    @TableField("order_status")
    private String orderStatus;
    @TableField("user_id")
    private Long userId;
    @TableField("payment_way")
    private String paymentWay = "ONLINE";
    @TableField("consignee")
    private String consignee;
    @TableField("consignee_tel")
    private String consigneeTel;
    @TableField("delivery_address")
    private String deliveryAddress;
    @TableField("total_amount")
    private BigDecimal totalAmount;
    @TableField("original_total_amount")
    private BigDecimal originalTotalAmount;
    @TableField("order_comment")
    private String orderComment;
    @TableField("out_trade_no")
    private String outTradeNo;
    @TableField("trade_body")
    private String tradeBody;
    @TableField("order_type")
    private String orderType = OrderTypeEnum.NORMAL_ORDER.name();
    @TableField("tracking_no")
    private String trackingNo;
    @TableField("refundable_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date refundableTime;
    @TableField("expire_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date expireTime;


    @TableField(exist = false)
    private List<OrderDetail> orderDetailList;
    @TableField(exist = false)
    private String wareId;

    public void sumTotalAmount(){
        BigDecimal totalAmount = new BigDecimal("0");
        for (OrderDetail orderDetail : orderDetailList) {
            BigDecimal skuTotalAmount = orderDetail.getOrderPrice().multiply(new BigDecimal(orderDetail.getSkuNum()));
            totalAmount = totalAmount.add(skuTotalAmount);
        }
        this.setTotalAmount(totalAmount);
        this.setOriginalTotalAmount(totalAmount);
    }
}
