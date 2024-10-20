package pers.wayease.duolaimall.pay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.client
 * @name OrderServiceClient
 * @description Order service client interface.
 * @since 2024-10-19 04:31
 */
@FeignClient(name = "wayease-duolaimall-order-service", url = "http://wayease-duolaimall-order-service.wayease-duolaimall.svc.cluster.local:8080")
public interface OrderServiceClient {

    @GetMapping("/api/order/getOrderInfo/{orderId}")
    public Result<OrderInfoDto> getOrderInfo(@PathVariable("orderId") Long orderId);

    @PostMapping("/api/order/success/{orderId}")
    public Result<Void> successPay(@PathVariable("orderId") Long orderId);
}
