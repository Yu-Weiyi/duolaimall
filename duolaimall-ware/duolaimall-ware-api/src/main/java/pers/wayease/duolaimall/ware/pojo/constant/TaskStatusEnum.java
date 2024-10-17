package pers.wayease.duolaimall.ware.pojo.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.constant
 * @name TaskStatusEnum
 * @description Task status enum.
 * @since 2024-10-16 21:06
 */
public enum TaskStatusEnum {

    PAID("已付款"),
    DEDUCTED("已减库存"),
    OUT_OF_STOCK("已付款，库存超卖"),
    DELEVERED("已出库"),
    SPLIT("已拆分");

    private  String comment;

    TaskStatusEnum(String comment) {
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
