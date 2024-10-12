package pers.wayease.duolaimall.search.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.search.service.UpdateService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.controller.api
 * @name ListUpdateController
 * @description List Update controller class.
 * @since 2024-10-10 11:04
 */
@Deprecated
@RestController
@RequestMapping("/api/list")
public class ListUpdateController {

    @Autowired
    private UpdateService updateService;

    @Deprecated
    @PostMapping("/upperGoods/{skuId}")
    Result<Void> upperGoods(@PathVariable("skuId") Long skuId) {
        updateService.upperGoods(skuId);
        return Result.ok();
    }

    @Deprecated
    @PostMapping("/lowerGoods/{skuId}")
    Result<Void> lowerGoods(@PathVariable("skuId") Long skuId) {
        updateService.lowerGoods(skuId);
        return Result.ok();
    }

    @Deprecated
    @PostMapping("/incrHotScore/{skuId}")
    Result<Void> incrHotScore(@PathVariable("skuId") Long skuId) {
        updateService.increaseHotScore(skuId);
        return Result.ok();
    }
}
