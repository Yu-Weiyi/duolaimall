package pers.wayease.duolaimall.cart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @GetMapping("/add/{skuId}/{skuNum}")
//    public Result<Void> addToCart(@PathVariable("skuId") Long skuId, @PathVariable Integer skuNum, HttpServletRequest httpServletRequest) {
//        String userId = getUserId(httpServletRequest);
//        cartService.addToCart(skuId, userId, skuNum);
//        return Result.ok();
//    }
//
//    @GetMapping("")
//    public Result<List<CartInfoDto>> cart(HttpServletRequest httpServletRequest) {
//        String userId = httpServletRequest.getHeader("userId");
//        String userTempId = httpServletRequest.getHeader("userTempId");
//        List<CartInfoDto> cartInfoDtoList = cartService.getCartList(userId, userTempId);
//        return Result.ok(cartInfoDtoList);
//    }
//
//    @PutMapping("/check/{skuId}/{isChecked}")
//    public Result checkCart(@PathVariable("skuId") Long skuId, @PathVariable("isChecked") Integer isChecked, HttpServletRequest httpServletRequest) {
//        String userId = getUserId(httpServletRequest);
//        cartService.checkCart(userId, isChecked, skuId);
//        return Result.ok();
//    }
//
//    private String getUserIdFromHeader(HttpServletRequest httpServletRequest) {
//        String userId = httpServletRequest.getHeader("userId");
//        if (StringUtils.isBlank(userId)) {
//            userId = httpServletRequest.getHeader("userTempId");
//        }
//        return userId;
//    }
}
