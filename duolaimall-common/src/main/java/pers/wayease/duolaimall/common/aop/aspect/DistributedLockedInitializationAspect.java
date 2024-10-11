package pers.wayease.duolaimall.common.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.aop.annotation.DistributedLockedInitialization;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.aop.aspect
 * @name DistributedLockedInitializationAspect
 * @description Distributed locked initialization aspect class.
 * @since 2024-10-11 16:05
 */
@Component
@Aspect
@Slf4j
public class DistributedLockedInitializationAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(pers.wayease.duolaimall.common.aop.annotation.DistributedLockedInitialization)")
    public void distributedLockedInitialization() {}

    @Around("distributedLockedInitialization()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DistributedLockedInitialization distributedLockedInitialization = method.getAnnotation(DistributedLockedInitialization.class);
        String distributedLockName = distributedLockedInitialization.distributedLockName();

        RLock rLock = redissonClient.getLock(distributedLockName);
        try {
            if (rLock.tryLock(10, 30, TimeUnit.SECONDS)) {
                try {
                    log.info("Lock on {}", distributedLockName);

                    // proceed
                    proceedingJoinPoint.proceed();

                } catch (Throwable e) {
                    log.error("Error in initialization period.");
                    e.printStackTrace();
                } finally {
                    rLock.unlock();
                    log.info("Lock off {}", distributedLockName);
                }
            } else {
                log.warn("Lock {} unavailable, initialization skipped.", distributedLockName);
            }
        } catch (InterruptedException e) {
            log.error("Lock {} error, initialization skipped.", distributedLockName);
        }
    }
}
