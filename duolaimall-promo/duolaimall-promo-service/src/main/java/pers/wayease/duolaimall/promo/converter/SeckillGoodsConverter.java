package pers.wayease.duolaimall.promo.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.order.pojo.dto.OrderDetailDto;
import pers.wayease.duolaimall.promo.pojo.dto.SeckillGoodsDto;
import pers.wayease.duolaimall.promo.pojo.modle.SeckillGoods;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.converter
 * @name SeckillGoodsConverter
 * @description Seckill goods converter interface.
 * @since 2024-10-21 03:17
 */
@Mapper(componentModel = "spring")
public interface SeckillGoodsConverter {

    SeckillGoodsDto convertSeckillGoodsToDto(SeckillGoods seckillGoods);

    List<SeckillGoodsDto> convertSeckillGoodsList(List<SeckillGoods> seckillGoodsList);

    SeckillGoods convertSeckillDto(SeckillGoodsDto seckillGoods);
    @Mapping(source = "seckillGoods.skuDefaultImg", target = "imgUrl")
    @Mapping(source = "seckillGoods.costPrice", target = "orderPrice")
    @Mapping(source = "num", target = "skuNum")
    OrderDetailDto secondKillGoodsToOrderDetailDto(SeckillGoods seckillGoods, Integer num);

    @Mapping(source = "seckillGoodsDto.skuDefaultImg", target = "imgUrl")
    @Mapping(source = "seckillGoodsDto.costPrice", target = "orderPrice")
    @Mapping(source = "num", target = "skuNum")
    OrderDetailDto secondKillGoodsDtoToOrderDetailDto(SeckillGoodsDto seckillGoodsDto, Integer num);
}
