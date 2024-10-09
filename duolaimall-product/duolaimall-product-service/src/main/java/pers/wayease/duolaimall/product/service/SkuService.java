package pers.wayease.duolaimall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuSaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.page.SkuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;
import pers.wayease.duolaimall.product.pojo.param.SkuInfoParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name SkuService
 * @description SKU service interface.
 * @since 2024-10-09 14:34
 */
public interface SkuService {

    void saveSkuInfo(SkuInfoParam skuInfoParam);

    SkuInfoPageDto getPage(Page<SkuInfo> pageParam);

    void onSale(Long skuId);

    void offSale(Long skuId);

    BigDecimal getPrice(Long skuId);

    List<SpuSaleAttributeInfoDto> getSpuSaleAttributeInfoList(Long spuId, Long skuId);

    List<PlatformAttributeInfoDto> getPlatformAttrInfoBySkuId(Long skuId);
}
