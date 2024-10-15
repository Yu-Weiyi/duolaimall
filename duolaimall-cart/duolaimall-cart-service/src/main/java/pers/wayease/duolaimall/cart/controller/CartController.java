package pers.wayease.duolaimall.cart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.cart.service.CartService;
import pers.wayease.duolaimall.common.result.Result;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.controller
 * @name CartController
 * @description Cart controller class.
 * @since 2024-10-14 10:14
 */
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/add/{skuId}/{skuNum}")
    public Result<Void> addToCart(@PathVariable("skuId") Long skuId, @PathVariable Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.ok();
    }

    @GetMapping("")
    public Result<List<CartInfoDto>> cart() {
        List<CartInfoDto> cartInfoDtoList = cartService.getCartList();
        return Result.ok(cartInfoDtoList);
    }

    @PutMapping("/check/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable("skuId") Long skuId, @PathVariable("isChecked") Integer isChecked) {
        cartService.checkCart(isChecked, skuId);
        return Result.ok();
    }

    @DeleteMapping("/{skuId}")
    public Result<Void> deleteCart(@PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.ok();
    }

    @DeleteMapping("/checked")
    public Result<Void> deleteChecked() {
        cartService.deleteChecked();
        return Result.ok();
    }
}
