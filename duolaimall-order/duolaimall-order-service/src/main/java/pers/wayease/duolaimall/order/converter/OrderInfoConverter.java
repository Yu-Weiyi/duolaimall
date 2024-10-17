package pers.wayease.duolaimall.order.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.order.pojo.model.OrderDetail;
import pers.wayease.duolaimall.order.pojo.model.OrderInfo;
import pers.wayease.duolaimall.order.pojo.param.OrderDetailParam;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDetailDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.converter
 * @name OrderInfoConverter
 * @description Order info converter interface.
 * @since 2024-10-16 05:42
 */
@Mapper(componentModel = "spring")
public interface OrderInfoConverter {

    OrderInfo orderInfoParam2Po(OrderInfoParam orderInfoParam);

    OrderDetail orderDetailParam2Po(OrderDetailParam orderDetailParam);

    OrderInfoDto orderInfoPo2Dto(OrderInfo orderInfo);

    List<OrderInfoDto> orderInfoPoList2DtoList(List<OrderInfo> orderInfoList);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "tradeBody", target = "orderBody" )
    @Mapping(source = "orderDetailList", target = "details")
    WareOrderTaskDto orderInfoPo2WareOrderTaskDto(OrderInfo orderInfo);

    WareOrderTaskDetailDto orderDetailPo2WareOrderTaskDetailDto(OrderDetail orderDetail);

    OrderInfo orderInfoDto2Po(OrderInfoDto orderInfoDto);
}
