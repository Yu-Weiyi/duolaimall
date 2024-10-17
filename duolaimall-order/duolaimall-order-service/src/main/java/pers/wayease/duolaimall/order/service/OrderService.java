package pers.wayease.duolaimall.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderTradeDto;
import pers.wayease.duolaimall.order.pojo.model.OrderInfo;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareSkuDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.service
 * @name OrderService
 * @description Order service interface.
 * @since 2024-10-15 22:21
 */
public interface OrderService {

    OrderTradeDto getTradeInfo();

    Long submitOrder(OrderInfoParam orderInfoParam);

    Long saveOrderInfo(OrderInfo orderInfo);

    IPage<OrderInfoDto> getPage(Page<OrderInfo> pageParam);

    OrderInfoDto getOrderInfo(Long orderId);

    void successPay(Long orderId);

    List<WareOrderTaskDto> orderSplit(String orderId, List<WareSkuDto> wareSkuDTOList);

    void successLockStock(String orderId, String taskStatus);
}
