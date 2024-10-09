package pers.wayease.duolaimall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.product.pojo.dto.page.TrademarkPageDto;
import pers.wayease.duolaimall.product.pojo.model.Trademark;
import pers.wayease.duolaimall.product.pojo.param.TrademarkParam;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name TrademarkService
 * @description Trademark service interface.
 * @since 2024-10-08 17:00
 */
public interface TrademarkService {

    void save(TrademarkParam trademarkParam);

    TrademarkPageDto getPage(Page<Trademark> pageParam);

    TrademarkDto getTrademarkByTmId(Long tmId);

    void updateById(TrademarkParam trademarkParam);

    void removeById(Long id);
}
