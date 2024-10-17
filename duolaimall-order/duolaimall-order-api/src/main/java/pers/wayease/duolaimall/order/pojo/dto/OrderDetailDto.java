package pers.wayease.duolaimall.order.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.dto
 * @name OrderDetailDto
 * @description Order detail DTO class.
 * @since 2024-10-15 21:58
 */
@Data
public class OrderDetailDto {

    Long id;
    Long orderId;
    Long skuId;
    String skuName;
    String imgUrl;
    BigDecimal orderPrice;
    Integer skuNum;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date updateTime;
}
