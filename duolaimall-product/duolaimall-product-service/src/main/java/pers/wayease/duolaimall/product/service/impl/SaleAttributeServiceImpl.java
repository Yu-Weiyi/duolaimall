package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.product.converter.SaleAttributeInfoConverter;
import pers.wayease.duolaimall.product.mapper.SaleAttributeInfoMapper;
import pers.wayease.duolaimall.product.pojo.dto.SaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.model.SaleAttributeInfo;
import pers.wayease.duolaimall.product.service.SaleAttributeService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name SaleAttributeServiceImpl
 * @description Sale attribute service implement class.
 * @since 2024-10-08 20:57
 */
@Service
public class SaleAttributeServiceImpl implements SaleAttributeService {

    @Autowired
    private SaleAttributeInfoMapper saleAttributeInfoMapper;
    @Autowired
    private SaleAttributeInfoConverter saleAttributeInfoConverter;

    @Override
    public List<SaleAttributeInfoDto> getSaleAttributeInfoList() {
        LambdaQueryWrapper<SaleAttributeInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SaleAttributeInfo::getIsDeleted, 0);
        List<SaleAttributeInfo> saleAttributeInfoList = saleAttributeInfoMapper.selectList(lambdaQueryWrapper);
        return saleAttributeInfoConverter.saleAttributeInfoPoList2DtoList(saleAttributeInfoList);
    }
}
