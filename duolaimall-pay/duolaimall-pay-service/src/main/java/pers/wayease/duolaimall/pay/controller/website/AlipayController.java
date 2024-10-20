package pers.wayease.duolaimall.pay.controller.website;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.pay.service.AlipayService;

import java.util.Map;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.controller.website
 * @name AlipayController
 * @description Alipay controller class.
 * @since 2024-10-19 04:39
 */
@RestController
@RequestMapping("/pay/alipay")
@Slf4j
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/submit/{orderId}")
    public String submitOrder(@PathVariable("orderId") Long orderId) throws AlipayApiException {
        String alipay = alipayService.createAlipay(orderId);
        return alipay;
    }

    @PostMapping("/callback/notify")
    @SneakyThrows
    public String callbackNotify(@RequestParam Map<String, String> paramMap) {
        log.info("Alipay callback notify, param map: {}", JSON.toJSONString(paramMap));
        String result = alipayService.callbackNotify(paramMap);
        return result;
    }
}
