package pers.wayease.duolaimall.promo.cache;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.cache
 * @name LocalCache
 * @description Local cache class.
 * @since 2024-10-21 02:56
 */
@Slf4j
public class LocalCache {

    private final static Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

    public static void put(String key, Object cacheObject) {
        cacheMap.put(key, cacheObject);
        log.info("local cacheMap:{}", JSON.toJSONString(cacheMap));
    }

    public static Object get(String key) {
        return cacheMap.get(key);
    }

    public static void remove(String key) {
        cacheMap.remove(key);
    }


    public static synchronized void removeAll() {
        cacheMap.clear();
    }
}
