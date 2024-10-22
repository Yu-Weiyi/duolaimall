package pers.wayease.duolaimall.promo.pojo.modle;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.pojo.modle
 * @name SeckillGoods
 * @description Seckill goods class.
 * @since 2024-10-21 02:27
 */
@TableName("seckill_goods")
@Data
public class SeckillGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("spu_id")
    private Long spuId;
    @TableField("sku_id")
    private Long skuId;
    @TableField("sku_name")
    private String skuName;
    @TableField("sku_default_img")
    private String skuDefaultImg;
    @TableField("price")
    private BigDecimal price;
    @TableField("cost_price")
    private BigDecimal costPrice;
    @TableField("create_time")
    private Date createTime;
    @TableField("check_time")
    private Date checkTime;
    @TableField("status")
    private String status;
    @TableField("start_time")
    private Date startTime;
    @TableField("end_time")
    private Date endTime;
    @TableField("num")
    private Integer num;
    @TableField("stock_count")
    private Integer stockCount;
    @TableField("sku_desc")
    private String skuDesc;
}
