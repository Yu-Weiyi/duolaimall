package pers.wayease.duolaimall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.wayease.duolaimall.product.pojo.dto.SpuImageDto;
import pers.wayease.duolaimall.product.pojo.dto.page.SpuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuSaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.model.SpuInfo;
import pers.wayease.duolaimall.product.pojo.param.SpuInfoParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name SpuService
 * @description SPU service interface.
 * @since 2024-10-08 21:06
 */
public interface SpuService {

    SpuInfoPageDto getSpuInfoPage(Page<SpuInfo> pageParam, Long thirdLevelCategoryId);

    void saveSpuInfo(SpuInfoParam spuInfoParam);

    List<SpuImageDto> getSpuImageList(Long spuId);

    List<SpuSaleAttributeInfoDto> getSpuSaleAttributeList(Long spuId);
}
