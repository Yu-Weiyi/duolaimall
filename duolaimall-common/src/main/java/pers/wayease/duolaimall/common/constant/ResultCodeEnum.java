package pers.wayease.duolaimall.common.constant;

import lombok.Getter;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.constant
 * @name ResultCodeEnum
 * @description Result code enum.
 * @since 2024-10-05 19:59
 */
@Getter
public enum ResultCodeEnum implements CodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(500, "服务异常"),
    ILLEGAL_REQUEST( 204, "非法请求"),
    //    PAY_RUN("205", "支付中"),
    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
//    SECKILL_NO_START(210, "秒杀还没开始"),

    // WaYease Error Code
    LOGIN_WITH_INEXIST_ACCOUNT(10001, "无此账户"),
    LOGIN_WITH_CONTROVERSIAL_ACCOUNT(10002, "该账户有争议，请咨询管理员"),
    LOGIN_WITH_UNMATCHED_PASSWORD(10003, "密码错误"),

    STOCK_NOT_ENOUGH(10004, "库存不足"),
    PRICE_HAS_CHANGED(10005, "价格已经变化"),

    THIRD_PARTY_ERROR(30000, "第三方服务异常"),
    SMTP_CONNECTION_ERROR(30001, "SMTP连接异常"),

    // promo
    SECKILL_RUN(211, "正在排队中"),
    SECKILL_DUPLICATE_TRADE(212, "重复抢购商品"),

    //    SECKILL_NO_PAY_ORDER(212, "您有未支付的订单"),
    SECKILL_FINISH(213, "已售罄"),

    SECKILL_GET_USER_ADDRESS_FAIL(214, "获取用户地址列表失败"),
    //    SECKILL_END(214, "秒杀已结束"),
    SECKILL_SUCCESS(215, "抢单成功"),
    SECKILL_FAIL(216, "抢单失败"),
    SECKILL_ILLEGAL(217, "请求不合法"),
    SECKILL_ORDER_SUCCESS(218, "下单成功"),
    SECKILL_ORDER_TRY_AGAIN(219, "请稍后重试");

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
