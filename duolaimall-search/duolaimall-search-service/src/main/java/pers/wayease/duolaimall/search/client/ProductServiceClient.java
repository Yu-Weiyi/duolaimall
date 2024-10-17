package pers.wayease.duolaimall.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.CategoryHierarchyDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.client
 * @name ProductServiceClient
 * @description Product service client interface.
 * @since 2024-10-10 11:29
 */
@FeignClient(name = "wayease-duolaimall-product-service", url = "http://wayease-duolaimall-product-service.wayease-duolaimall.svc.cluster.local:8080")
public interface ProductServiceClient {

    @GetMapping("/api/product/getSkuInfo/{skuId}")
    Result<SkuInfoDto> getSkuInfo(@PathVariable("skuId") Long skuId);

    @GetMapping("/api/product/getCategoryHierarchy/{thirdLevelCategoryId}")
    Result<CategoryHierarchyDto> getCategoryHierarchy(@PathVariable("thirdLevelCategoryId") Long thirdLevelCategoryId);

    @GetMapping("/api/product/getPlatformAttributeInfoList/{skuId}")
    Result<List<PlatformAttributeInfoDto>> getPlatformAttributeInfoList(@PathVariable("skuId") Long skuId);

    @GetMapping("/api/product/getTrademark/{tmId}")
    Result<TrademarkDto> getTrademarkMarkDto(@PathVariable("tmId") Long tmId);

    @GetMapping("/api/product/getAllOnSaleSkuIdList")
    Result<List<Long>> getAllOnSaleSkuIdList();
}
