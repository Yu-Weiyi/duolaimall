package pers.wayease.duolaimall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.product.pojo.dto.page.TrademarkPageDto;
import pers.wayease.duolaimall.product.pojo.model.Trademark;
import pers.wayease.duolaimall.product.pojo.param.TrademarkParam;
import pers.wayease.duolaimall.product.service.TrademarkService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller
 * @name AdminBaseTrademarkController
 * @description Admin base trademark controller class.
 * @since 2024-10-08 16:52
 */
@RestController
@RequestMapping("/admin/product/baseTrademark")
public class AdminBaseTrademarkController {

    @Autowired
    private TrademarkService trademarkService;

    @PostMapping("/save")
    public Result<Void> saveTrademark(@RequestBody TrademarkParam trademarkParam) {

        trademarkService.save(trademarkParam);
        return Result.ok();
    }

    @GetMapping("/{pageNo}/{pageSize}")
    public Result<TrademarkPageDto> getPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {

        Page<Trademark> pageParam = new Page<>(pageNo, pageSize);
        TrademarkPageDto trademarkPageDto = trademarkService.getPage(pageParam);
        return Result.ok(trademarkPageDto);
    }

    @GetMapping("/get/{id}")
    public Result<TrademarkDto> getTrademark(@PathVariable Long id) {

        TrademarkDto trademarkDto = trademarkService.getTrademarkByTmId(id);
        return Result.ok(trademarkDto);
    }

    @PutMapping("/update")
    public Result<Void> updateTrademark(@RequestBody TrademarkParam trademarkParam) {

        trademarkService.updateById(trademarkParam);
        return Result.ok();
    }

    @DeleteMapping("/remove/{tradeMarkId}")
    public Result<Void> removeTrademark(@PathVariable Long tradeMarkId) {

        trademarkService.removeById(tradeMarkId);
        return Result.ok();
    }
}
