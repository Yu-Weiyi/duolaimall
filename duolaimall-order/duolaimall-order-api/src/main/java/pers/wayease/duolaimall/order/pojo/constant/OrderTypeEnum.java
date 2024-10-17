package pers.wayease.duolaimall.order.pojo.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.constant
 * @name OrderTypeEnum
 * @description Order type enum.
 * @since 2024-10-15 21:36
 */
public enum OrderTypeEnum {

    NORMAL_ORDER("普通订单"),
    PROMO_ORDER("秒杀订单");

    String describe;

    OrderTypeEnum(String desc) {
        this.describe = desc;
    }

    public String getDesc() {
        return describe;
    }
}
