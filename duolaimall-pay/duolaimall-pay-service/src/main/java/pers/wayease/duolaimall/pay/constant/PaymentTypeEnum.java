package pers.wayease.duolaimall.pay.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.constant
 * @name PaymentTypeEnum
 * @description Payment type enum.
 * @since 2024-10-19 03:44
 */
public enum PaymentTypeEnum {

    ALIPAY("支付宝"),
    WEIXINPAY("微信支付" );

    private String comment ;

    PaymentTypeEnum(String comment) {
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
