package pers.wayease.duolaimall.pay.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.pay.constant.PaymentTypeEnum;
import pers.wayease.duolaimall.pay.thirdparty.alipay.AlipayHelper;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.thirdparty
 * @name PayHelperFactory
 * @description Pay helper factory class.
 * @since 2024-10-19 06:02
 */
@Component
public class PayHelperFactory {

    @Autowired
    private AlipayHelper alipayHelper;

//    @Autowired
//    private WechatpayHelper wechatpayHelper;

    public PayHelper createPayHelper(PaymentTypeEnum paymentTypeEnum) {
        switch (paymentTypeEnum) {
            case ALIPAY:
                return alipayHelper;
//            case WEIXINPAY:
//                return wechatpayHelper;
            default:
                return null;
        }
    }
}
