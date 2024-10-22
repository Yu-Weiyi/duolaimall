package pers.wayease.duolaimall.pay.controller.api;

import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.pay.pojo.dto.PaymentInfoDto;
import pers.wayease.duolaimall.pay.service.AlipayService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.controller.api
 * @name ApiPayController
 * @description API pay controller classs.
 * @since 2024-10-21 03:58
 */
@RestController
@RequestMapping("/api/pay")
public class ApiPayController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/getPaymentInfoByOutTradeNo/{outTradeNo}")
    PaymentInfoDto getPaymentInfoDtoByOutTradeNo(@PathVariable(value = "outTradeNo") String outTradeNo){
        PaymentInfoDto paymentInfoDto = alipayService.getPaymentInfoDtoByOutTradeNo(outTradeNo);
        return paymentInfoDto;
    }

    @GetMapping("/getAlipayInfo/{outTradeNo}")
    Result getAlipayInfo(@PathVariable(value = "outTradeNo") String outTradeNo) throws AlipayApiException {
        String alipayInfo = alipayService.getAlipayInfo(outTradeNo);
        return Result.ok(alipayInfo);
    }

    @GetMapping("/closeAlipay/{outTradeNo}")
    Result closeAlipay(@PathVariable(value = "outTradeNo") String outTradeNo){
        return null;
    }

    @GetMapping("/closePaymentInfo/{outTradeNo}")
    Result closePaymentInfo(@PathVariable(value = "outTradeNo") String outTradeNo){
        alipayService.closePaymentInfo(outTradeNo);
        return  Result.ok();
    }
}
