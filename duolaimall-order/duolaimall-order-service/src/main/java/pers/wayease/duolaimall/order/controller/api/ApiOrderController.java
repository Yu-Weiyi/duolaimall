package pers.wayease.duolaimall.order.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.order.service.OrderService;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareSkuDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.controller.api
 * @name ApiOrderController
 * @description API order controller class.
 * @since 2024-10-16 20:02
 */
@RestController
@RequestMapping("/api/order")
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrderInfo/{orderId}")
    public Result<OrderInfoDto> getOrderInfo(@PathVariable("orderId") Long orderId) {
        OrderInfoDto orderInfoDto = orderService.getOrderInfo(orderId);
        return Result.ok(orderInfoDto);
    }

    @PostMapping("/success/{orderId}")
    public Result<Void> successPay(@PathVariable("orderId") Long orderId) {
        orderService.successPay(orderId);
        return Result.ok();
    }

    @PostMapping("/orderSplit/{orderId}")
    public Result<List<WareOrderTaskDto>> orderSplit(@PathVariable("orderId") String orderId, @RequestBody List<WareSkuDto> wareSkuDtoList) {
        List<WareOrderTaskDto> wareOrderTaskDtoList = orderService.orderSplit(orderId, wareSkuDtoList);
        return Result.ok(wareOrderTaskDtoList);
    }

    @PostMapping("/successLockStock/{orderId}/{teskStatus}")
    public Result<Void> successLockStock(@PathVariable("orderId") String orderId, @PathVariable("taskStatus") String taskStatus) {
        orderService.successLockStock(orderId, taskStatus);
        return Result.ok();
    }
}
