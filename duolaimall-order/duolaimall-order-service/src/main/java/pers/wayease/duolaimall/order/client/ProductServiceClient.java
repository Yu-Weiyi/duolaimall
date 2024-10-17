package pers.wayease.duolaimall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.wayease.duolaimall.common.result.Result;

import java.math.BigDecimal;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.client
 * @name ProductServiceClient
 * @description Product service client interface.
 * @since 2024-10-16 05:12
 */
@FeignClient(name = "wayease-duolaimall-product-service", url = "http://wayease-duolaimall-product-service.wayease-duolaimall.svc.cluster.local:8080")
public interface ProductServiceClient {

    @GetMapping("/api/product/getSkuPrice/{skuId}")
    public Result<BigDecimal> getSkuPrice(@PathVariable("skuId") Long skuId);
}
