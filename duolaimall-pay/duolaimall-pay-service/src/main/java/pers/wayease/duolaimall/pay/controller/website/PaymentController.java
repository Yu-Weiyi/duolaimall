package pers.wayease.duolaimall.pay.controller.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.pay.client.OrderServiceClient;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.controller.website
 * @name PaymentController
 * @description Payment controller class.
 * @since 2024-10-19 04:28
 */
@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private OrderServiceClient orderServiceClient;

    @GetMapping("/auth")
    public Result<OrderInfoDto> payIndex(Long orderId) {
        OrderInfoDto orderInfoDto = orderServiceClient.getOrderInfo(orderId).getData();
        return Result.ok(orderInfoDto);
    }
}
