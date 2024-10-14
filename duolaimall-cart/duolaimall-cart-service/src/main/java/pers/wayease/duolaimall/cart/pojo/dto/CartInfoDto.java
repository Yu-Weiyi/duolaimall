package pers.wayease.duolaimall.cart.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.pojo.dto
 * @name CartInfoDto
 * @description Cart info DTO class.
 * @since 2024-10-14 10:26
 */
@Data
public class CartInfoDto {

    private static final long serialVersionUID = 1L;

    private String userId;
    private Long skuId;
    private BigDecimal cartPrice;
    private Integer skuNum;
    private String imgUrl;
    private String skuName;
    // 1： 选中  0：未选中
    private Integer isChecked = 1;
    BigDecimal skuPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
