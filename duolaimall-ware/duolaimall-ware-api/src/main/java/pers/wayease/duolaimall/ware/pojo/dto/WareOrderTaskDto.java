package pers.wayease.duolaimall.ware.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.dto
 * @name WareOrderTaskDto
 * @description Ware order task DTO class.
 * @since 2024-10-16 05:48
 */
@Data
public class WareOrderTaskDto {

    private String orderId;
    private String consignee;
    private String consigneeTel;
    private String deliveryAddress;
    private String orderComment;
    private String paymentWay;
    private String taskStatus;
    private String orderBody;
    private String trackingNo;
    private String wareId;
    private String taskComment;
    private List<WareOrderTaskDetailDto> details;
}
