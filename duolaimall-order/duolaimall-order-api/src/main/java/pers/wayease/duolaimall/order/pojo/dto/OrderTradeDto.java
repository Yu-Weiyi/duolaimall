package pers.wayease.duolaimall.order.pojo.dto;

import lombok.Data;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.dto
 * @name OrderTradeDto
 * @description Order trade DTO class.
 * @since 2024-10-15 21:53
 */
@Data
public class OrderTradeDto {

    List<UserAddressDto> userAddressList;
    List<OrderDetailDto> detailArrayList;
    Integer totalNum;
    BigDecimal totalAmount;
}
