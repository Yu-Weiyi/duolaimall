package pers.wayease.duolaimall.ware.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.order.pojo.dto.OrderDetailDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDetailDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;
import pers.wayease.duolaimall.ware.pojo.model.WareOrderTask;
import pers.wayease.duolaimall.ware.pojo.model.WareOrderTaskDetail;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.converter
 * @name WareOrderTaskConverter
 * @description Ware order task converter interface.
 * @since 2024-10-16 21:00
 */
@Mapper(componentModel = "spring")
public interface WareOrderTaskConverter {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "tradeBody", target = "orderBody")
    @Mapping(source = "orderDetailList", target = "details")
    WareOrderTask orderInfoDto2wareOrderTask(OrderInfoDto orderInfoDto);

    WareOrderTaskDetail orderDetailDto2WareOrderTaskDetail(OrderDetailDto orderDetailDto);

    WareOrderTask wareOrderTaskDto2Po(WareOrderTaskDto wareOrderTaskDto);

    WareOrderTaskDetail wareOrderTaskDetailDto2Po(WareOrderTaskDetailDto wareOrderTaskDetailDto);

    List<WareOrderTask> wareOrderTaskDtoList2PoList(List<WareOrderTaskDto> wareOrderTaskDtoList);
}
