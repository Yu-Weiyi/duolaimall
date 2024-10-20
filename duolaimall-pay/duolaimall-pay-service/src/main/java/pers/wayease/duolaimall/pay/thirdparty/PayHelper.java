package pers.wayease.duolaimall.pay.thirdparty;

import com.alipay.api.AlipayApiException;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.thirdparty
 * @name PayHelper
 * @description Pay helper interface.
 * @since 2024-10-19 06:01
 */
public interface PayHelper {

    String createClient(OrderInfoDto orderInfoDto) throws AlipayApiException;

    String getTradeStatus(String outTradeNo) throws AlipayApiException;

    boolean closeAlipay(String outTradeNo) throws AlipayApiException;
}
