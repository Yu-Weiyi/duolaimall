package pers.wayease.duolaimall.ware.service;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.service
 * @name WareService
 * @description Ware servcice interface.
 * @since 2024-10-15 20:56
 */
public interface WareService {

    Boolean hasStock(Long skuId, Integer num);
}
