package pers.wayease.duolaimall.product.service;

import pers.wayease.duolaimall.product.pojo.dto.ProductDetailDto;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name ProductDetailService
 * @description Product detail service interface.
 * @since 2024-10-09 17:11
 */
public interface ProductDetailService {

    ProductDetailDto getItemBySkuId(Long skuId);
}
