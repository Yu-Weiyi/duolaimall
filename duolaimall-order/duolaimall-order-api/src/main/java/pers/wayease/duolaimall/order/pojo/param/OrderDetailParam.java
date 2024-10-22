package pers.wayease.duolaimall.order.pojo.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.param
 * @name OrderDetailParam
 * @description Order detail param class.
 * @since 2024-10-15 22:01
 */
@Data
public class OrderDetailParam {

    private Long id;
    private Long orderId;
    private Long skuId;
    private String skuName;
    private String imgUrl;
    private BigDecimal orderPrice;
    private Integer skuNum;
}
