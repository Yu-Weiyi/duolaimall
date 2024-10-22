package pers.wayease.duolaimall.promo.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.constant
 * @name LocalCacheStockStatusEnum
 * @description Local cache stock status enum.
 * @since 2024-10-21 02:24
 */
public enum LocalCacheStockStatusEnum {

    HAS_STOCK(1,"有库存"),
    NO_STOCK(0,"没库存");

    private int code;
    private String msg;

    LocalCacheStockStatusEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
