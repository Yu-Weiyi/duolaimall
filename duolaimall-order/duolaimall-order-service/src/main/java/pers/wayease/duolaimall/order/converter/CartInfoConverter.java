package pers.wayease.duolaimall.order.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderDetailDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.order.pojo.model.OrderInfo;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.converter
 * @name CartInfoConverter
 * @description Cart info converter interface.
 * @since 2024-10-16 03:55
 */
@Mapper(componentModel = "spring")
public interface CartInfoConverter {

    @Mapping(source = "skuPrice", target = "orderPrice")
    OrderDetailDto cartInfoDto2OrderDetailDto(CartInfoDto cartInfoDto);

    List<OrderDetailDto> cartInfoDtoList2OrderDetailDtoList(List<CartInfoDto> cartInfoDtoList);

    @Mapping(source = "dtoRecords", target = "records")
    Page<OrderInfoDto> orderInfoDtoList2DtoPage(Page<OrderInfo> orderInfoPage, List<OrderInfoDto> dtoRecords);
}
