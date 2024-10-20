package pers.wayease.duolaimall.ware.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareSkuDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.client
 * @name OrderServiceClient
 * @description Order service client interface.
 * @since 2024-10-16 20:37
 */
@FeignClient(name = "wayease-duolaimall-order-service", url = "http://wayease-duolaimall-order-service.wayease-duolaimall.svc.cluster.local:8080")
public interface OrderServiceClient {

    @GetMapping("/api/order/getOrderInfo/{orderId}")
    public Result<OrderInfoDto> getOrderInfo(@PathVariable("orderId") Long orderId);

    @PostMapping("/api/order/orderSplit/{orderId}")
    public Result<List<WareOrderTaskDto>> orderSplit(@PathVariable("orderId") String orderId, @RequestBody List<WareSkuDto> wareSkuDtoList);

    @PostMapping("/api/order/successLockStock/{orderId}/{taskStatus}")
    public Result<Void> successLockStock(@PathVariable("orderId") String orderId, @PathVariable("taskStatus") String taskStatus);
}
