package pers.wayease.duolaimall.promo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.util.DateUtil;
import pers.wayease.duolaimall.order.pojo.dto.OrderDetailDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderTradeDto;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.promo.cache.LocalCache;
import pers.wayease.duolaimall.promo.cache.RedisStockOper;
import pers.wayease.duolaimall.promo.client.OrderServiceClient;
import pers.wayease.duolaimall.promo.client.UserServiceClient;
import pers.wayease.duolaimall.promo.constant.LocalCacheStockStatusEnum;
import pers.wayease.duolaimall.promo.constant.SeckillGoodsStatusEnum;
import pers.wayease.duolaimall.promo.converter.SeckillGoodsConverter;
import pers.wayease.duolaimall.promo.mapper.SeckillGoodsMapper;
import pers.wayease.duolaimall.promo.mq.PromoTransactionProducer;
import pers.wayease.duolaimall.promo.pojo.dto.SeckillGoodsDto;
import pers.wayease.duolaimall.promo.pojo.modle.SeckillGoods;
import pers.wayease.duolaimall.promo.service.PromoService;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.service.impl
 * @name PromoServiceImpl
 * @description Promo service implement class.
 * @since 2024-10-21 02:51
 */
@Service
@Slf4j
public class PromoServiceImpl implements PromoService {

    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    SeckillGoodsConverter seckillGoodsConverter;

    @Autowired
    UserServiceClient userServiceClient;
    @Autowired
    OrderServiceClient orderServiceClient;

    @Autowired
    PromoTransactionProducer promoTransactionProducer;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisStockOper redisStockOper;
    
    @Override
    public void importIntoRedis() {

        List<SeckillGoods> seckillGoods = getTodaySeckillList();

        RMap<Long, SeckillGoods> seckillListMap = redissonClient.getMap(RedisConstant.PROMO_SECKILL_GOODS);

        RMap<Long, Integer> stockMap = redissonClient.getMap(RedisConstant.PROMO_SECKILL_GOODS_STOCK, new StringCodec());

        seckillListMap.clear();
        stockMap.clear();
        LocalCache.removeAll();
        for (SeckillGoods seckillGood : seckillGoods) {
            LocalCache.put(String.valueOf(seckillGood.getSkuId()), LocalCacheStockStatusEnum.HAS_STOCK.getCode());
            seckillListMap.put(seckillGood.getSkuId(),seckillGood);
            stockMap.put(seckillGood.getSkuId(), seckillGood.getStockCount());
        }
    }

    private List<SeckillGoods> getTodaySeckillList() {
        QueryWrapper<SeckillGoods> seckillWrapper=new QueryWrapper<>();
        seckillWrapper.eq("status", SeckillGoodsStatusEnum.CHECKED_PASS.name());
        seckillWrapper.gt("stock_count",0);

        Date current= DateUtil.datePlusHours(new Date(),8);
        log.debug("current time: {}", current);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(current);
        seckillWrapper.eq("DATE_FORMAT(start_time,'%Y-%m-%d')", format);

        List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectList(seckillWrapper);
        return seckillGoods;
    }

    @Override
    public SeckillGoodsDto getSeckillGoodsDto(Long skuId) {
        RMap<Long, SeckillGoods> seckillListMap = redissonClient.getMap(RedisConstant.PROMO_SECKILL_GOODS);
        SeckillGoods seckillGoods = seckillListMap.get(skuId);
        SeckillGoodsDto seckillGoodsDto = seckillGoodsConverter.convertSeckillGoodsToDto(seckillGoods);
        return seckillGoodsDto;
    }

    @Override
    public boolean checkOrder(Long skuId, String userId) {
        RMap<String, Long> submitMap = redissonClient.getMap(RedisConstant.PROMO_SUBMIT_ORDER);
        String key=userId+skuId;
        Long msgId = submitMap.get(key);
        return msgId!=null;
    }

    @Override
    public void clearRedisCache() {
        RMap<String, String> stockMap = redissonClient.getMap(RedisConstant.PROMO_SECKILL_GOODS_STOCK, new StringCodec());
        List<SeckillGoods> todaySeckillList = getTodaySeckillList();
        for (SeckillGoods seckillGoods : todaySeckillList) {
            seckillGoods.setStockCount(Integer.valueOf(stockMap.get(String.valueOf(seckillGoods.getSkuId()))));
            seckillGoodsMapper.updateById(seckillGoods);
        }
        stockMap.clear();
        LocalCache.removeAll();
        redissonClient.getKeys().deleteByPattern("promo:*");
    }

    @Override
    public OrderTradeDto getTradeData(String userId, Long skuId) {

        //TODO use redis
        List<UserAddressDto> userAddressListByUserId = userServiceClient.getUserAddressListByUserId(Long.valueOf(userId)).getData();
        SeckillGoodsDto seckillGoodsDto = getSeckillGoodsDto(skuId);
        OrderDetailDto orderDetailDto = seckillGoodsConverter.secondKillGoodsDtoToOrderDetailDto(seckillGoodsDto, 1);
        OrderTradeDto orderTradeDto=new OrderTradeDto();
        orderTradeDto.setUserAddressList(userAddressListByUserId);
        orderTradeDto.setDetailArrayList(Arrays.asList(orderDetailDto));
        orderTradeDto.setTotalNum(1);
        orderTradeDto.setTotalAmount(orderDetailDto.getOrderPrice());
        return orderTradeDto;
    }

    @Override
    public boolean submitOrder(OrderInfoParam orderInfo) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        reduceStock(orderInfo);
        boolean result = promoTransactionProducer.sendMsg(TopicConstant.SECKILL_GOODS_QUEUE_TOPIC.name(), orderInfo);

        return result;
    }

    @Override
    public boolean reduceStock(OrderInfoParam orderInfo) {

        try {
            Long skuId = orderInfo.getOrderDetailList().get(0).getSkuId();
            Integer skuNum = orderInfo.getOrderDetailList().get(0).getSkuNum();


            Long i = redisStockOper.decrRedisStock(skuId, skuNum);

            if (i<0){
                return false;
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void submitOrderInTransaction(OrderInfoParam orderInfo) {
        // do nothing
    }

    @Override
    public List<SeckillGoodsDto> findAll() {
        RMap<Long, SeckillGoods> seckillListMap = redissonClient.getMap(RedisConstant.PROMO_SECKILL_GOODS);
        //这个地方把库存也存一下，库存也用map
        RMap<String, String> stockMap = redissonClient.getMap(RedisConstant.PROMO_SECKILL_GOODS_STOCK, new StringCodec());
        List<SeckillGoods> seckillGoods = seckillListMap.values().stream().map((item)->{
            Long skuId = item.getSkuId();
            String stock = stockMap.get(String.valueOf(skuId));
            item.setStockCount(Integer.valueOf(stock));
            return item;
        }).collect(Collectors.toList());
        return seckillGoodsConverter.convertSeckillGoodsList(seckillGoods);
    }
}

