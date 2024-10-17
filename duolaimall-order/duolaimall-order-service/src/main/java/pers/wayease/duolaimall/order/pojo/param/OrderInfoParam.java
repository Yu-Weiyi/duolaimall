package pers.wayease.duolaimall.order.pojo.param;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.param
 * @name OrderInfoParam
 * @description Order info param class.
 * @since 2024-10-15 22:00
 */
@Data
public class OrderInfoParam {

    private Long userId;
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private String orderComment;
    private String paymentWay = "ONLINE";
    private List<OrderDetailParam> orderDetailList;
}
