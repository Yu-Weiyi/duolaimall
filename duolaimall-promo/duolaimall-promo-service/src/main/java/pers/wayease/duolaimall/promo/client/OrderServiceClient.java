package pers.wayease.duolaimall.promo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.client
 * @name OrderServiceClient
 * @description Order service client interface.
 * @since 2024-10-21 02:58
 */
@FeignClient(name = "wayease-duolaimall-order-service", url = "http://wayease-duolaimall-order-service.wayease-duolaimall.svc.cluster.local:8080")
public interface OrderServiceClient {

    @PostMapping("/api/order/seckill/submitOrder")
    public Result<Long> submitSeckillOrder(@RequestBody OrderInfoParam orderInfo);
}
