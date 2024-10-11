package pers.wayease.duolaimall.product.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.page.SkuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;
import pers.wayease.duolaimall.product.pojo.param.SkuInfoParam;
import pers.wayease.duolaimall.product.service.SkuService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller.admin
 * @name SkuController
 * @description SKU controller class.
 * @since 2024-10-09 14:11
 */
@RestController
@RequestMapping("/admin/product")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @PostMapping("/saveSkuInfo")
    public Result<Void> saveSkuInfo(@RequestBody SkuInfoParam skuInfoParam) {
        skuService.saveSkuInfo(skuInfoParam);
        return Result.ok();
    }

    @GetMapping("/list/{page}/{limit}")
    public Result<SkuInfoPageDto> getPage(@PathVariable Integer page, @PathVariable Integer limit) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        SkuInfoPageDto skuInfoPageDto = skuService.getSkuInfoPage(pageParam);
        return Result.ok(skuInfoPageDto);
    }

    @GetMapping("/onSale/{skuId}")
    public Result<Void> onSale(@PathVariable Long skuId) {
        skuService.onSale(skuId);
        return Result.ok();
    }

    @GetMapping("/cancelSale/{skuId}")
    public Result<Void> cancelSale(@PathVariable Long skuId) {
        skuService.offSale(skuId);
        return Result.ok();
    }
}
