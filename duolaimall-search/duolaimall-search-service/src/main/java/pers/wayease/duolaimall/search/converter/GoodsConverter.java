package pers.wayease.duolaimall.search.converter;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.search.pojo.dto.GoodsDto;
import pers.wayease.duolaimall.search.pojo.dto.SearchAttributeDto;
import pers.wayease.duolaimall.search.pojo.model.Goods;
import pers.wayease.duolaimall.search.pojo.model.SearchAttribute;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.converter
 * @name GoodsConverter
 * @description Goods converter interface.
 * @since 2024-10-10 20:24
 */
@Mapper(componentModel = "spring")
public interface GoodsConverter {

    GoodsDto goodsPo2Dto(Goods goods);

    List<GoodsDto> goodsPoList2DtoList(List<Goods> goodsList);

    SearchAttributeDto searchAttributePo2Dto(SearchAttribute searchAttribute);
}
