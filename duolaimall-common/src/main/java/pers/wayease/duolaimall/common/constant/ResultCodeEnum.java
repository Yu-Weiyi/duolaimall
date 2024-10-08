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
    THIRD_PARTY_ERROR(30000, "第三方服务异常"),
    SMTP_CONNECTION_ERROR(30001, "SMTP连接异常");

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
