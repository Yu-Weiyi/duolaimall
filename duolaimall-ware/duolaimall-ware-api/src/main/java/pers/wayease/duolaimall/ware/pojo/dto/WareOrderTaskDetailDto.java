package pers.wayease.duolaimall.ware.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.dto
 * @name WareOrderTaskDetailDto
 * @description Ware order task detail DTO class.
 * @since 2024-10-16 05:49
 */
@Data
public class WareOrderTaskDetailDto {

    private String skuId;
    private String skuName;
    private Integer skuNum;
    private Long taskId;
}
