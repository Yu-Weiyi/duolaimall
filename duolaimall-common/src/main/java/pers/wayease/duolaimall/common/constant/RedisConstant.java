package pers.wayease.duolaimall.common.constant;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.constant
 * @name RedisConstant
 * @description Redis constant class.
 * @since 2024-10-10 17:07
 */
public class RedisConstant {

    // cache
    public static final String FIRST_LEVEL_CATRGORY = "first:level:category";
    public static final String SECOND_LEVEL_CATRGORY = "second:level:category";
    public static final String THIRD_LEVEL_CATRGORY = "third:level:category";
    public static final String CATEGORY_TREE_LIST = "category:tree:list";
    public static final String CATEGORY_HIERARCHY = "category:hierarchy";
    public static final String SKU_SALE_ATTRIBUTE_INFO_LIST = "sku:sale:attribute:info:list";
    public static final String SPU_SALE_ATTRIBUTE_MAP = "spu:sale:attribute:map";
    public static final String SPU_POSTER = "spu:poster";
    public static final String PLATFORM_ATTRIBUTE_INFO_LIST = "platform:attribute:info:list";

    // hot score
    public static final String HOT_SCORE = "hot:score";

    // bloom filter
    public static final String SKU_BLOOM_FILTER = "sku:bloom:filter";

    // distributed lock
    public static final String SKU_BLOOM_FILTER_INITIALIZATION_LOCK = "lock:sku:bloom:filter:initialization";
    public static final String GOODS_ES_INITIALIZATION_LOCK = "lock:goods:es:initialization";

    // auth jwt
    public static final String AUTH_ID_JWT = "auth:id:jwt";

    // store
    public static final String ORDER_TRADE_CODE = "order:trade:code";

    // pay
    public static final String PAY_CALLBACK_NOTIFY = "pay:callback:notify";

    // promo
    public static final String PROMO_SECKILL_GOODS = "promo:seckill:goods";
    public static final String PROMO_SECKILL_GOODS_STOCK = "promo:seckill:goods:stock";
    public static final String PROMO_SUBMIT_ORDER = "promo:submit:order";
    public static final String PROMO_USER_ORDERED_FLAG = "promo:user:ordered:flag";
    public static final String REDUCE_STOCK_STATE = "reduce:stock:state";
}
