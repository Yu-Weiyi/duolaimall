package pers.wayease.duolaimall.product.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.CategoryHierarchyDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.product.service.CategoryService;
import pers.wayease.duolaimall.product.service.SkuService;
import pers.wayease.duolaimall.product.service.TrademarkService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller.api
 * @name ApiProductController
 * @description API Product controller class.
 * @since 2024-10-10 12:42
 */
@RestController
@RequestMapping("/api/product")
public class ApiProductController {

    @Autowired
    private SkuService skuService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TrademarkService trademarkService;

    @GetMapping("/getSkuInfo/{skuId}")
    public Result<SkuInfoDto> getSkuInfo(@PathVariable("skuId") Long skuId) {
        SkuInfoDto skuInfoDto = skuService.getSkuInfo(skuId);
        return Result.ok(skuInfoDto);
    }

    @GetMapping("/getCategoryHierarchy/{thirdLevelCategoryId}")
    public Result<CategoryHierarchyDto> getCategoryHierarchy(@PathVariable("thirdLevelCategoryId") Long thirdLevelCategoryId) {
        CategoryHierarchyDto categoryHierarchyDto = categoryService.getCategortHierarchyByThirdLevelCategoryId(thirdLevelCategoryId);
        return Result.ok(categoryHierarchyDto);
    }

    @GetMapping("/getPlatformAttributeInfoList/{skuId}")
    public Result<List<PlatformAttributeInfoDto>> getPlatformAttributeInfoList(@PathVariable("skuId") Long skuId) {
        List<PlatformAttributeInfoDto> platformAttributeInfoDtoList = skuService.getPlatformAttrInfoBySkuId(skuId);
        return Result.ok(platformAttributeInfoDtoList);
    }

    @GetMapping("/getTrademark/{tmId}")
    public Result<TrademarkDto> getTrademarkMarkDto(@PathVariable("tmId") Long tmId) {
        TrademarkDto trademarkDto = trademarkService.getTrademarkByTmId(tmId);
        return Result.ok(trademarkDto);
    }

    @GetMapping("/getAllOnSaleSkuIdList")
    public Result<List<Long>> getAllOnSaleSkuIdList() {
        List<Long> skuIdList = skuService.getAllOnSaleSkuIdList();
        return Result.ok(skuIdList);
    }

    @GetMapping("/getSkuPrice/{skuId}")
    public Result<BigDecimal> getSkuPrice(@PathVariable("skuId") Long skuId) {
        BigDecimal skuPrice = skuService.getSkuPrice(skuId);
        return Result.ok(skuPrice);
    }
}
