package pers.wayease.duolaimall.pay.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.pojo.dto
 * @name PaymentInfoDto
 * @description Payment info DTO class.
 * @since 2024-10-19 03:40
 */
@Data
public class PaymentInfoDto {

    private Long id;
    private String outTradeNo;
    private Long orderId;
    private Long userId;
    private String paymentType;
    private String tradeNo;
    private BigDecimal totalAmount;
    private String subject;
    private String paymentStatus;
    private Date createTime;
    private Date callbackTime;
    private String callbackContent;
}
