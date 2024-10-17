package pers.wayease.duolaimall.order.pojo.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.constant
 * @name OrderStatusEnum
 * @description Order status enum.
 * @since 2024-10-16 09:12
 */
public enum OrderStatusEnum {

    UNPAID("未支付"),
    PAID("已支付"),
    NOTIFIED_WARE("已通知仓储"),
    WAIT_DELEVER("待发货"),
    STOCK_EXCEPTION("库存异常"),
    DELEVERED("已发货"),
    CLOSED("已关闭"),
    COMMENT("已评价"),
    PAY_FAIL("支付失败"),
    SPLIT("已拆分");

    String describe;

    OrderStatusEnum(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public static String getStatusDescribeByStatus(String status) {
        OrderStatusEnum arrObj[] = OrderStatusEnum.values();
        for (OrderStatusEnum obj : arrObj) {
            if (obj.name().equals(status)) {
                return obj.getDescribe();
            }
        }
        return null;
    }
}
