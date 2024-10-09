package pers.wayease.duolaimall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.product.pojo.model.SpuInfo;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mapper
 * @name SpuInfoMapper
 * @description SPU info mapper interface.
 * @since 2024-10-08 21:24
 */
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {

    List<SpuInfo> selectListByspuInfoIdList(@Param("spuInfoIdList") List<Long> spuInfoIdList);
}
