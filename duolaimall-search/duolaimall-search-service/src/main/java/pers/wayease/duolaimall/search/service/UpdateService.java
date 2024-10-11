package pers.wayease.duolaimall.search.service;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.service
 * @name UpdateService
 * @description Update service interface.
 * @since 2024-10-10 11:12
 */
public interface UpdateService {

    void upperGoods(Long skuId);

    void lowerGoods(Long skuId);

    void increaseHotScore(Long skuId);
}
