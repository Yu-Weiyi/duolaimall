package pers.wayease.duolaimall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.product.pojo.param.CategoryTrademarkParam;
import pers.wayease.duolaimall.product.service.CategoryService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller
 * @name AdminBaseCategoryTrademarkController
 * @description Admin base category trademark controller class.
 * @since 2024-10-08 19:23
 */
@RestController
@RequestMapping("/admin/product/baseCategoryTrademark")
public class AdminBaseCategoryTrademarkController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public Result<Void> save(@RequestBody CategoryTrademarkParam categoryTrademarkParam) {

        categoryService.save(categoryTrademarkParam);
        return Result.ok();
    }

    @GetMapping("/findTrademarkList/{thirdLevelCategoryId}")
    public Result<List<TrademarkDto>> findTrademarkList(@PathVariable Long thirdLevelCategoryId) {

        List<TrademarkDto> trademarkDtoList = categoryService.findTrademarkList(thirdLevelCategoryId);
        return Result.ok(trademarkDtoList);
    }

    @GetMapping("/findCurrentTrademarkList/{thirdLevelCategoryId}")
    public Result<List<TrademarkDto>> findCurrentTrademarkList(@PathVariable Long thirdLevelCategoryId) {

        List<TrademarkDto> trademarkDtoList = categoryService.findUnLinkedTrademarkList(thirdLevelCategoryId);
        return Result.ok(trademarkDtoList);
    }

    @DeleteMapping("/remove/{thirdLevelCategoryId}/{trademarkId}")
    public Result<Void> remove(@PathVariable Long thirdLevelCategoryId, @PathVariable Long trademarkId) {

        categoryService.remove(thirdLevelCategoryId, trademarkId);
        return Result.ok();
    }
}
