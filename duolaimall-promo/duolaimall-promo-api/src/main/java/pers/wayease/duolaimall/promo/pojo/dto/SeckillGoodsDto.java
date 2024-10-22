package pers.wayease.duolaimall.promo.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.pojo.dto
 * @name SeckillGoodsDto
 * @description Seckill goods DTO class.
 * @since 2024-10-21 02:34
 */
@Data
public class SeckillGoodsDto implements Serializable {

    private Long id;
    private Long spuId;
    private Long skuId;
    private String skuName;
    private String skuDefaultImg;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Date checkTime;
    private String status;
    private Date startTime;
    private Date endTime;
    private Integer num;
    private Integer stockCount;
    private String skuDesc;
}
