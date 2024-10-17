package pers.wayease.duolaimall.order.pojo.dto;

import lombok.Data;
import pers.wayease.duolaimall.order.pojo.constant.OrderTypeEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.dto
 * @name OrderInfoDto
 * @description Order info DTO class.
 * @since 2024-10-15 22:04
 */
@Data
public class OrderInfoDto {

    private Long id;
    private Long parentOrderId;
    private String orderStatus;
    private Long userId;
    private String paymentWay;
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private BigDecimal originalTotalAmount;
    private String orderComment;
    private String outTradeNo;
    private String tradeBody;
    private String orderType = OrderTypeEnum.NORMAL_ORDER.name();
    private String trackingNo;
    private Date refundableTime;
    private Date createTime;
    private Date updateTime;
    private Date expireTime;
    private List<OrderDetailDto> orderDetailList;
    private String wareId;
    private String orderStatusName;
}
