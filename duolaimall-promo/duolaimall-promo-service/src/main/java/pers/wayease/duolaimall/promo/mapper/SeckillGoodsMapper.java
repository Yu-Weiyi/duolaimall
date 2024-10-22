package pers.wayease.duolaimall.promo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import pers.wayease.duolaimall.promo.pojo.modle.SeckillGoods;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.mapper
 * @name SeckillGoodsMapper
 * @description Seckill goods mapper interface.
 * @since 2024-10-21 02:29
 */
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {

    int updateStockUseSkuId(@Param("skuId") Long skuId, @Param("currentDate") String dateStr, @Param("stock") int stockCount);
}
