package pers.wayease.duolaimall.product.service;

import pers.wayease.duolaimall.product.pojo.dto.SaleAttributeInfoDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name SaleAttributeService
 * @description Sale attribute service interface.
 * @since 2024-10-08 20:55
 */
public interface SaleAttributeService {

    List<SaleAttributeInfoDto> getSaleAttributeInfoList();
}
