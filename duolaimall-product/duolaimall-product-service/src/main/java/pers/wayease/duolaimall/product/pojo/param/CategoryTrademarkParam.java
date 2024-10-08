package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name CategoryTrademarkParam
 * @description Category trademark param class.
 * @since 2024-10-08 19:32
 */
@Data
public class CategoryTrademarkParam {

    private Long category3Id;
    private List<Long> trademarkIdList;
}
