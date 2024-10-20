package pers.wayease.duolaimall.pay.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.service
 * @name AlipayService
 * @description Alipay service interface.
 * @since 2024-10-19 04:42
 */
public interface AlipayService {

    String createAlipay(Long orderId) throws AlipayApiException;

    String callbackNotify(Map<String, String> paramsMap) throws AlipayApiException;
}
