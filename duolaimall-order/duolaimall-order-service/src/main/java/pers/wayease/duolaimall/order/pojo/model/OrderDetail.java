package pers.wayease.duolaimall.order.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

import java.math.BigDecimal;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.pojo.model
 * @name OrderDetail
 * @description Order detail class.
 * @since 2024-10-15 21:32
 */
@TableName("order_detail")
@Data
public class OrderDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("order_id")
    private Long orderId;
    @TableField("sku_id")
    private Long skuId;
    @TableField("sku_name")
    private String skuName;
    @TableField("img_url")
    private String imgUrl;
    @TableField("order_price")
    private BigDecimal orderPrice;
    @TableField("sku_num")
    private Integer skuNum;

    @TableField(exist = false)
    private String hasStock;
}
