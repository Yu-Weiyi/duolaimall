package pers.wayease.duolaimall.promo.mq;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.promo.cache.RedisStockOper;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.mq
 * @name PromoTransactionProducer
 * @description Promo transaction producer class.
 * @since 2024-10-21 03:26
 */
@Component
@Slf4j
public class PromoTransactionProducer {

    @Value("${rocketmq.namesrv.addr}")
    String namesrvAddr;

    @Value("${rocketmq.producer.group}")
    String producerGroup;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisStockOper redisStockOper;
//    @Autowired
//    SeckillGoodsMapper seckillGoodsMapper;

    TransactionMQProducer transactionMQProducer;
    @PostConstruct
    public void  init() throws MQClientException {
        transactionMQProducer = new TransactionMQProducer(producerGroup);
        transactionMQProducer.setNamesrvAddr(namesrvAddr);
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
                // 消息id
                String transactionId = message.getTransactionId();
                //这里是事务执行的回调
                OrderInfoParam orderInfo= (OrderInfoParam) arg;
                boolean result = reduceStock(orderInfo);
                LocalTransactionState state = result ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
                //这个地方要存入redis内
                String key= RedisConstant.REDUCE_STOCK_STATE + transactionId;
                RBucket<LocalTransactionState> bucket = redissonClient.getBucket(key);
                bucket.set(state);
                log.info("接收到消息:"+transactionId + "状态是："+state);
                return result ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                try {
                    String transactionId = messageExt.getTransactionId();
                    log.info("接收到消息，id为："+transactionId);
                    String key= RedisConstant.REDUCE_STOCK_STATE + ":" + transactionId;
                    RBucket<String> bucket = redissonClient.getBucket(key);
                    String state = bucket.get();
                    if (state==null){
                        return LocalTransactionState.UNKNOW;
                    }
                    return LocalTransactionState.ROLLBACK_MESSAGE.equals(state)?LocalTransactionState.ROLLBACK_MESSAGE:LocalTransactionState.COMMIT_MESSAGE;
                }catch (Exception e){
                    log.warn("ex", e);
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }
        });
        transactionMQProducer.start();
    }

    public boolean sendMsg(String topic,Object msgObj) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        String jsonString = JSON.toJSONString(msgObj);
        Message message=new Message(topic, jsonString.getBytes(StandardCharsets.UTF_8));


        OrderInfoParam orderInfo= (OrderInfoParam) msgObj;
        TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(message, orderInfo);

        SendStatus sendStatus = sendResult.getSendStatus();
        LocalTransactionState localTransactionState = sendResult.getLocalTransactionState();


        return SendStatus.SEND_OK.equals(sendStatus)&&LocalTransactionState.COMMIT_MESSAGE.equals(localTransactionState);
    }

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
}
