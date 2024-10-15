package pers.wayease.duolaimall.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.ware.pojo.model.WareSku;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.mapper
 * @name WareSkuMapper
 * @description Ware SKU mapper interface.
 * @since 2024-10-15 20:40
 */
public interface WareSkuMapper extends BaseMapper<WareSku> {

    public Integer selectStockBySkuId(@Param("skuId") String skuId);

    public Integer selectStockBySkuIdForUpdate(@Param("wareSku") WareSku wareSku);

    public void increaseStockLocked(@Param("wareSku") WareSku wareSku);
}
