package pers.wayease.duolaimall.ware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.ware.mapper.WareSkuMapper;
import pers.wayease.duolaimall.ware.service.WareService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.service.impl
 * @name WareServiceImpl
 * @description Ware service implement class.
 * @since 2024-10-15 20:57
 */
@Service
public class WareServiceImpl implements WareService {

    @Autowired
    private WareSkuMapper wareSkuMapper;

    @Override
    public Boolean hasStock(Long skuId, Integer num) {
        Integer stock = wareSkuMapper.selectStockBySkuId(String.valueOf(skuId));
        if (stock == null || stock < num) {
            // stock not enough
            return false;
        }
        // stock enough
        return true;
    }
}
