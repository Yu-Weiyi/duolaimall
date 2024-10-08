package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.product.converter.TrademarkConverter;
import pers.wayease.duolaimall.product.mapper.TrademarkMapper;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkPageDto;
import pers.wayease.duolaimall.product.pojo.model.Trademark;
import pers.wayease.duolaimall.product.pojo.param.TrademarkParam;
import pers.wayease.duolaimall.product.service.TrademarkService;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name TrademarkServiceImpl
 * @description Trademark service implement class.
 * @since 2024-10-08 17:03
 */
@Service
public class TrademarkServiceImpl implements TrademarkService {

    @Autowired
    private TrademarkMapper trademarkMapper;

    @Autowired
    private TrademarkConverter trademarkConverter;

    @Override
    public void save(TrademarkParam trademarkParam) {
        trademarkMapper.insert(trademarkConverter.trademarkParam2Po(trademarkParam));
    }

    @Override
    public TrademarkPageDto getPage(Page<Trademark> pageParam) {
        Page<Trademark> trademarkPage = trademarkMapper.selectPage(pageParam, null);
        return trademarkConverter.trademarkPoPage2DtoPage(trademarkPage);
    }

    @Override
    public TrademarkDto getTrademarkByTmId(Long tmId) {
        Trademark trademark = trademarkMapper.selectById(tmId);
        return trademarkConverter.trademarkPo2Dto(trademark);
    }

    @Override
    public void updateById(TrademarkParam trademarkParam) {
        trademarkMapper.updateById(trademarkConverter.trademarkParam2Po(trademarkParam));
    }

    @Override
    public void removeById(Long id) {
        trademarkMapper.deleteById(id);
    }
}
