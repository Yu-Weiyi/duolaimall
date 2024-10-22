package pers.wayease.duolaimall.promo.cache;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.RedisConstant;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.cache
 * @name RedisStockOper
 * @description Redis stock oper class.
 * @since 2024-10-21 03:20
 */
@Component
@Slf4j
public class RedisStockOper {

    @Autowired
    RedissonClient redissonClient;

    private final static String STOCK_LUA_NAME = "Redis扣减库存脚本";

    private final static String STOCK_LUA = "local c_s = redis.call('hget', KEYS[1], ARGV[1]) \n" +
            "if not c_s or tonumber(c_s) < tonumber(ARGV[2]) then \n" +
            "return -1 end \n" +
            "return redis.call('hincrby',KEYS[1], ARGV[1], -tonumber(ARGV[2]))";

    private String sha1;

    @PostConstruct
    public void loadScript(){
        sha1 = redissonClient.getScript().scriptLoad(STOCK_LUA);
        log.info("load script {}", sha1);
    }

    public Long decrRedisStock(Long skuId, Integer count) {
        return redissonClient.getScript(StringCodec.INSTANCE).evalSha(RScript.Mode.READ_WRITE,
                sha1,
                RScript.ReturnType.INTEGER,
                List.of(RedisConstant.PROMO_SECKILL_GOODS_STOCK),
                skuId.toString(), count.toString());
    }
}
