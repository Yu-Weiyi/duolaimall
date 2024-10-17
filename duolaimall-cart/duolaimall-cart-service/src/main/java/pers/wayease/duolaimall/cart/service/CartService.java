package pers.wayease.duolaimall.cart.service;

import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.service
 * @name CartService
 * @description Cart service interface.
 * @since 2024-10-14 20:12
 */
public interface CartService {

    void addToCart(Long skuId, Integer skuNum);

    List<CartInfoDto> getCartList();

    void checkCart(Integer isChecked, Long skuId);

    void deleteCart(Long skuId);

    void deleteChecked();

    List<CartInfoDto> getCheckedCartList();

    void delete(List<Long> skuIdList);

    void refreshCartPrice(Long skuId);
}
