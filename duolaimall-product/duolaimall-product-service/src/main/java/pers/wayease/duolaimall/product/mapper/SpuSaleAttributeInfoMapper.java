package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.product.pojo.model.SpuSaleAttributeInfo;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name SpuSaleAttributeInfoMapper
 * @description SPU sale attribute info mapper interface.
 * @since 2024-10-09 09:08
 */
public interface SpuSaleAttributeInfoMapper extends BaseMapper<SpuSaleAttributeInfo> {

    List<SpuSaleAttributeInfo> selectObjectListBySpuId(@Param("spuId") Long spuId);

    List<SpuSaleAttributeInfo> selectCheckedObject(@Param("spuId") Long spuId, @Param("skuId") Long skuId);
}
