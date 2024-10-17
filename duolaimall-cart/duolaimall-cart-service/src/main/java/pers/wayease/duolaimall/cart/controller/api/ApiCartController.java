package pers.wayease.duolaimall.cart.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.cart.service.CartService;
import pers.wayease.duolaimall.common.context.UserContext;
import pers.wayease.duolaimall.common.result.Result;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.controller.api
 * @name ApiCartController
 * @description API cart controller class.
 * @since 2024-10-15 22:53
 */
@RestController
@RequestMapping("/api/cart")
public class ApiCartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getCheckedCartList/{userId}")
    public List<CartInfoDto> getCheckedCartList(@PathVariable("userId") String userId) {
        UserContext.setUserId(Long.valueOf(userId));
        return cartService.getCheckedCartList();
    }

    @GetMapping("/refresh/{userId}/{skuId}")
    public Result<Void> refreshCartPrice(@PathVariable("userId") String userId, @PathVariable("skuId") Long skuId) {
        UserContext.setCartUserId(userId);
        cartService.refreshCartPrice(skuId);
        return Result.ok();
    }

    @PutMapping("/delete/order/cart/{userId}")
    public Result<Void> removeCartProductsInOrder(@PathVariable("userId") String userId, @RequestBody List<Long> skuIdList) {
        UserContext.setCartUserId(userId);
        cartService.delete(skuIdList);
        return Result.ok();
    }
}
