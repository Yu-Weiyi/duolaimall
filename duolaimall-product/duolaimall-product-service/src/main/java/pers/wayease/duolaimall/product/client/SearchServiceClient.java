package pers.wayease.duolaimall.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pers.wayease.duolaimall.common.result.Result;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.client
 * @name SearchServiceClient
 * @description Search service client interface, deprecated.
 * @since 2024-10-10 21:00
 */
@Deprecated
@FeignClient(name = "wayease-duolaimall-search-service", url = "http://wayease-duolaimall-search-service.wayease-duolaimall.svc.cluster.local:8080")
public interface SearchServiceClient {

    @Deprecated
    @PostMapping("/api/list/upperGoods/{skuId}")
    Result<Void> upperGoods(@PathVariable("skuId") Long skuId);

    @Deprecated
    @PostMapping("/api/list/lowerGoods/{skuId}")
    Result<Void> lowerGoods(@PathVariable("skuId") Long skuId);

    @Deprecated
    @PostMapping("/api/list/incrHotScore/{skuId}")
    Result<Void> incrHotScore(@PathVariable("skuId") Long skuId);
}
