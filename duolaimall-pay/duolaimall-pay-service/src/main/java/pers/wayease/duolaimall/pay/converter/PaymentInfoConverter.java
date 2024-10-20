package pers.wayease.duolaimall.pay.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.pay.pojo.dto.PaymentInfoDto;
import pers.wayease.duolaimall.pay.pojo.model.PaymentInfo;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.converter
 * @name PaymentInfoConverter
 * @description Payment info converter interface.
 * @since 2024-10-19 04:24
 */
@Mapper(componentModel = "spring")
public interface PaymentInfoConverter {

    @Mapping(source = "id",target = "orderId")
    @Mapping(source = "tradeBody",target = "subject")
    @Mapping(source = "totalAmount",target = "totalAmount")
    @Mapping(source = "userId",target = "userId")
    @Mapping(source = "outTradeNo",target = "outTradeNo")
    PaymentInfo orderInfoDto2PaymentInfo(OrderInfoDto orderInfoDto);

    PaymentInfoDto paymentInfoPo2Dto(PaymentInfo paymentInfo);
}
