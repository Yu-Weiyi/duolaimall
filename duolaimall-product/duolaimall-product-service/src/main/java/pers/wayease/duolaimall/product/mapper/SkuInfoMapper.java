package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name SkuInfoMapper
 * @description SKU info mapper interface.
 * @since 2024-10-09 14:41
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    SkuInfo selectObjectById(@Param("skuInfoId") Long skuInfoId);

    List<SkuInfo> selectObjectListByIdList(@Param("skuInfoIdList") List<Long> skuInfoIdList);
}
