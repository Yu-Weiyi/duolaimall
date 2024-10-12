package pers.wayease.duolaimall.common.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.aop.annotation.Cache;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.aop.aspect
 * @name CacheAspect
 * @description Cache aspect class.
 * @since 2024-10-12 19:40
 */
@Component
@Aspect
@Slf4j
public class CacheAspect {
    
    @Autowired
    RedissonClient redissonClient;
    
    @Pointcut("@annotation(pers.wayease.duolaimall.common.aop.annotation.Cache)")
    public void cache() {};
    
    @Around("cache()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Cache annotation = method.getAnnotation(Cache.class);
        String key = annotation.prefix();

        // parse key if args exist
        Object[] argArray = proceedingJoinPoint.getArgs();
        if (argArray != null && argArray.length > 0) {
            String paramString = Arrays.asList(argArray).toString();
            key += ":" + paramString;
        }

        log.info("Cache key: {}", key);

        RBucket<Object> rBucket = redissonClient.getBucket(key);

        Object cachedObject = rBucket.get();
        if (cachedObject != null) {
            // cached
            log.info("Cache hit on {}", key);
            return cachedObject;
        }
        
        // uncached
        log.info("Cache miss on {}", key);
        String lockKey = "lock:" + key;
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            rLock.lock();
            RBucket<Object> doubleCheckBucket = redissonClient.getBucket(key);
            cachedObject = doubleCheckBucket.get();
            if (cachedObject != null) {
                return cachedObject;
            }
            
            cachedObject = proceedingJoinPoint.proceed();
            
            if (cachedObject == null) {
                Class<?> type = method.getReturnType();
                if (List.class.isAssignableFrom(type)) {
                    cachedObject = new ArrayList<>();
                } else if (Map.class.isAssignableFrom(type)) {
                    cachedObject = new HashMap<>();
                } else {
                    Constructor<?> constructor = type.getConstructor();
                    cachedObject = constructor.newInstance();
                }
            }
            doubleCheckBucket.set(cachedObject);
        } catch (Throwable e) {
            log.warn("Cache failed on {}", key, e);
        } finally {
            if (rLock != null) {
                rLock.unlock();
            }
        }
        return cachedObject;
    }
}
