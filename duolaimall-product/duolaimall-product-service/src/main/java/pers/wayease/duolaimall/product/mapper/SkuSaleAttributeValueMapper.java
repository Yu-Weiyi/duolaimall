package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.product.pojo.dto.SkuSaleAttributeValuePermutationDto;
import pers.wayease.duolaimall.product.pojo.model.SkuSaleAttributeValue;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name SkuSaleAttributeValueMapper
 * @description SKU sale attribute value mapper interface.
 * @since 2024-10-09 15:00
 */
public interface SkuSaleAttributeValueMapper extends BaseMapper<SkuSaleAttributeValue> {

    // TODO rewrite SQL
    List<SkuSaleAttributeValuePermutationDto> selectPermutationObjectBySpuId(@Param("spuId") Long spuId);
}
