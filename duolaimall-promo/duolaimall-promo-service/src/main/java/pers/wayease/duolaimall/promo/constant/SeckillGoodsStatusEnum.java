package pers.wayease.duolaimall.promo.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.constant
 * @name SeckillGoodsStatusEnum
 * @description Seckill goods status enum.
 * @since 2024-10-21 02:26
 */
public enum SeckillGoodsStatusEnum {

    UNCKECKED("未审核"),
    CHECKED_PASS("审核通过"),
    CHECKED_UNPASS("审核不通过"),
    FINISHED("已结束");

    String desc;

    SeckillGoodsStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
