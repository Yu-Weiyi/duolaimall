package pers.wayease.duolaimall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.common.result.Result;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.client
 * @name CartServiceClient
 * @description Cart service client interface.
 * @since 2024-10-15 22:48
 */
@FeignClient(name = "wayease-duolaimall-cart-service", url = "http://wayease-duolaimall-cart-service.wayease-duolaimall.svc.cluster.local:8080")
public interface CartServiceClient {

    @GetMapping("/api/cart/getCheckedCartList/{userId}")
    public List<CartInfoDto> getCheckedCartList(@PathVariable("userId") String userId);

    @GetMapping("/api/cart/refresh/{userId}/{skuId}")
    public Result<Void> refreshCartPrice(@PathVariable("userId") String userId, @PathVariable("skuId") Long skuId);

    @PutMapping("/api/cart/delete/order/cart/{userId}")
    public Result<Void> removeCartProductsInOrder(@PathVariable("userId") String userId, @RequestBody List<Long> skuIdList);

}
