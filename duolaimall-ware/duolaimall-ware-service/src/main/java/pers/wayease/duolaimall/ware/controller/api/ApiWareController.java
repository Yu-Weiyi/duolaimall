package pers.wayease.duolaimall.ware.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.ware.service.WareService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.controller.api
 * @name ApiWareController
 * @description API ware controller class.
 * @since 2024-10-15 20:20
 */
@RestController
@RequestMapping("/api/ware")
public class ApiWareController {

    @Autowired
    private WareService wareService;

    @GetMapping("/hasStock/{skuId}/{num}")
    public Result<Boolean> hasStock(@PathVariable("skuId") Long skuId, @PathVariable("num") Integer num) {
        Boolean hasStock = wareService.hasStock(skuId, num);
        return Result.ok(hasStock);
    }

    @PostMapping("/decreaseStock/{orderId}")
    public Result<Void> decreaseStock(@PathVariable("orderId") Long orderId) {
        wareService.decreaseStock(orderId);
        return Result.ok();
    }
}
