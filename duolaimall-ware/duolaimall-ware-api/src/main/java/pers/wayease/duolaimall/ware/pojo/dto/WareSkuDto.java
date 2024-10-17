package pers.wayease.duolaimall.ware.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.dto
 * @name WareSkuDto
 * @description Ware SKU DTO class.
 * @since 2024-10-16 20:09
 */
@Data
public class WareSkuDto {

    String wareId;
    List<String> skuIds;
}
