package pers.wayease.duolaimall.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.pay.pojo.dto.PaymentInfoDto;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.client
 * @name PayServiceClient
 * @description Pay service client interface.
 * @since 2024-10-21 03:55
 */
@FeignClient(name = "wayease-duolaimall-pay-service", url = "http://wayease-duolaimall-pay-service.wayease-duolaimall.svc.cluster.local:8080")
public interface PayServiceClient {

    @GetMapping("/api/pay/getPaymentInfoByOutTradeNo/{outTradeNo}")
    PaymentInfoDto getPaymentInfoDtoByOutTradeNo(@PathVariable(value = "outTradeNo") String outTradeNo);

    @GetMapping("/api/pay/getAlipayInfo/{outTradeNo}")
    Result getAlipayInfo(@PathVariable(value = "outTradeNo") String outTradeNo);

    @GetMapping("/api/pay/closeAlipay/{outTradeNo}")
    Result closeAlipay(@PathVariable(value = "outTradeNo") String outTradeNo);

    @GetMapping("/api/pay/closePaymentInfo/{outTradeNo}")
    Result closePaymentInfo(@PathVariable(value = "outTradeNo") String outTradeNo);
}
