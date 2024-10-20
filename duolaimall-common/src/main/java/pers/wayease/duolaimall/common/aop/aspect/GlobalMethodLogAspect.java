package pers.wayease.duolaimall.common.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.context.DebugTraceContext;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.aop.aspect
 * @name GlobalMethodLogAspect
 * @description Global method log aspect class.
 * @since 2024-10-20 09:15
 */
@Aspect
@Component
@Slf4j
public class GlobalMethodLogAspect {

    // all method
    @Around("execution(* pers.wayease.duolaimall..*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();

        // enter log
        StringBuilder enterLog = new StringBuilder(DebugTraceContext.getNextStyledTraceId() + " Method " + className + "." + methodName + "(");
        for (int i = 0; i < methodArgs.length; i++) {
            if (i != 0) {
                enterLog.append(", ");
            }
            enterLog.append(parameterTypes[i].getSimpleName() + " " + methodArgs[i]);
        }
        enterLog.append(") entered.");
        log.debug(enterLog.toString());

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.warn(DebugTraceContext.getNextStyledTraceId() + " Method " + className + "." + methodName + " threw exception: {}.", throwable.getMessage(), throwable);
            throw throwable;
        }

        if (result == null) {
            log.debug(DebugTraceContext.getNextStyledTraceId() + " Method " + className + "." + methodName + " returned void");
        } else {
            log.debug(DebugTraceContext.getNextStyledTraceId() + " Method " + className + "." + methodName + " returned " + result.getClass().getSimpleName() + " " + result + ".");
        }

        return result;
    }
}
