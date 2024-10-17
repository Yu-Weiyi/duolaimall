package pers.wayease.duolaimall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pers.wayease.duolaimall.common.result.Result;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.client
 * @name WareServiceClient
 * @description Ware service client interface.
 * @since 2024-10-16 04:26
 */
@FeignClient(name = "wayease-duolaimall-ware-service", url = "http://wayease-duolaimall-ware-service.wayease-duolaimall.svc.cluster.local:8080")
public interface WareServiceClient {

    @GetMapping("/api/ware/hasStock/{skuId}/{num}")
    public Result<Boolean> hasStock(@PathVariable("skuId") Long skuId, @PathVariable("num") Integer num);

    @PostMapping("/api/ware/decreaseStock/{orderId}")
    public Result<Void> decreaseStock(@PathVariable("orderId") Long orderId);
}
