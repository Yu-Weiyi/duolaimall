package pers.wayease.duolaimall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.product.pojo.dto.SaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuImageDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuSaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.model.SpuInfo;
import pers.wayease.duolaimall.product.pojo.param.SpuInfoParam;
import pers.wayease.duolaimall.product.service.SaleAttributeService;
import pers.wayease.duolaimall.product.service.SpuService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.controller
 * @name AdminSpuController
 * @description Admin SPU controller class.
 * @since 2024-10-08 20:07
 */
@RestController
@RequestMapping("/admin/product")
public class AdminSpuController {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SaleAttributeService saleAttributeService;

    @GetMapping("/baseSaleAttrList")
    public Result<List<SaleAttributeInfoDto>> getBaseSaleAttrList() {

        List<SaleAttributeInfoDto> saleAttributeInfoDtoList = saleAttributeService.getSaleAttributeInfoList();
        return Result.ok(saleAttributeInfoDtoList);
    }

    @GetMapping("/{page}/{size}")
    public Result<SpuInfoPageDto> findSpuInfoPage(@PathVariable Integer page, @PathVariable Integer size, @RequestParam Long category3Id) {

        Page<SpuInfo> pageParam = new Page<>(page, size);
        SpuInfoPageDto spuInfoPageDto = spuService.getSpuInfoPage(pageParam, category3Id);
        return Result.ok(spuInfoPageDto);
    }

    @PostMapping("/saveSpuInfo")
    public Result<Void> saveSpuInfo(@RequestBody SpuInfoParam spuInfoParam) {

        spuService.saveSpuInfo(spuInfoParam);
        return Result.ok();
    }

    @GetMapping("/spuImageList/{spuId}")
    public Result<List<SpuImageDto>> getSpuImageList(@PathVariable Long spuId) {

        List<SpuImageDto> spuImageDtoList = spuService.getSpuImageList(spuId);
        return Result.ok(spuImageDtoList);
    }

    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result<List<SpuSaleAttributeInfoDto>> getSaleAttributeInfoList(@PathVariable Long spuId) {

        List<SpuSaleAttributeInfoDto> spuSaleAttributeInfoDtoList = spuService.getSpuSaleAttributeList(spuId);
        return Result.ok(spuSaleAttributeInfoDtoList);
    }
}
