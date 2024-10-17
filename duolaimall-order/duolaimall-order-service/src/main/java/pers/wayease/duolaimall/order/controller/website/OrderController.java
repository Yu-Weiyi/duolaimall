package pers.wayease.duolaimall.order.controller.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderTradeDto;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.order.service.OrderService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.controller.website
 * @name OrderController
 * @description Order controller class.
 * @since 2024-10-15 21:40
 */
@RestController
@RequestMapping("/order/auth")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/trade")
    public Result<OrderTradeDto> trade() {
        OrderTradeDto orderTradeDto = orderService.getTradeInfo();
        return Result.ok(orderTradeDto);
    }

    @PostMapping("/submitOrder")
    public Result<Long> submitOrder(@RequestBody OrderInfoParam orderInfoParam) {
        Long orderId = orderService.submitOrder(orderInfoParam);
        return Result.ok(orderId);
    }

    @GetMapping("/{page}/{limit}")
    public Result<IPage<OrderInfoDto>> getPage(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        IPage<OrderInfoDto> orderInfoDtoIPage = orderService.getPage(new Page<>(page, limit));
        return Result.ok(orderInfoDtoIPage);
    }
}
