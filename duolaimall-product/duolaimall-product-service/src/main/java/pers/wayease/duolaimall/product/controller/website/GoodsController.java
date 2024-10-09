package pers.wayease.duolaimall.product.controller.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.ProductDetailDto;
import pers.wayease.duolaimall.product.service.ProductDetailService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller.website
 * @name GoodsController
 * @description Goods controller class.
 * @since 2024-10-09 17:04
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/{skuId}")
    public Result<ProductDetailDto> getProductDetail(@PathVariable Long skuId) {
        ProductDetailDto productDetailDto = productDetailService.getItemBySkuId(skuId);
        return Result.ok(productDetailDto);
    }
}
