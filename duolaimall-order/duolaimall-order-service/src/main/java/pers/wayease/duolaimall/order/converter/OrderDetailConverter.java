package pers.wayease.duolaimall.order.converter;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.order.pojo.dto.OrderDetailDto;
import pers.wayease.duolaimall.order.pojo.model.OrderDetail;
import pers.wayease.duolaimall.order.pojo.param.OrderDetailParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.converter
 * @name OrderDetailConverter
 * @description Order detail converter interface.
 * @since 2024-10-16 10:29
 */
@Mapper(componentModel = "spring")
public interface OrderDetailConverter {

    OrderDetail orderDetailParam2Po(OrderDetailParam orderDetailParam);

    OrderDetailDto orderDetailPo2Dto(OrderDetail orderDetail);

    List<OrderDetailDto> orderDetailPoList2DtoList(List<OrderDetail> orderDetailList);

    OrderDetail orderDetailDto2Po(OrderDetailDto orderDetailDto);

    OrderDetail orderDetailCopy(OrderDetail orderDetail);
}
