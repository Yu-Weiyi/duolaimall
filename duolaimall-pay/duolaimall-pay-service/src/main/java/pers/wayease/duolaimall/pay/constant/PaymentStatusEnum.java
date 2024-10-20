package pers.wayease.duolaimall.pay.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.constant
 * @name PaymentStatusEnum
 * @description Payment status enum.
 * @since 2024-10-19 03:46
 */
public enum PaymentStatusEnum {

    UNPAID("支付中"),
    PAID("已支付"),
    PAY_FAIL("支付失败"),
    CLOSED("已关闭");

    private String name ;

    PaymentStatusEnum(String name) {
        this.name=name;
    }
}
