package pers.wayease.duolaimall.product.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.mapstruct.Mapper;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkPageDto;
import pers.wayease.duolaimall.product.pojo.model.Trademark;
import pers.wayease.duolaimall.product.pojo.param.TrademarkParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter
 * @name TrademarkConverter
 * @description Trademark converter interface.
 * @since 2024-10-08 17:19
 */
@Mapper(componentModel = "spring")
public interface TrademarkConverter {

    Trademark trademarkParam2Po(TrademarkParam trademarkParam);

    TrademarkDto trademarkPo2Dto(Trademark trademark);

    List<TrademarkDto> trademarkPoList2DtoList(List<Trademark> trademarkList);

    TrademarkPageDto trademarkPoPage2DtoPage(IPage<Trademark> trademarkPage);
}
