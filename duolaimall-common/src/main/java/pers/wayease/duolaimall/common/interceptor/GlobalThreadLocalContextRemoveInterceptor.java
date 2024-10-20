package pers.wayease.duolaimall.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.wayease.duolaimall.common.context.DebugTraceContext;
import pers.wayease.duolaimall.common.context.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.interceptor
 * @name GlobalThreadLocalContextRemoveInterceptor
 * @description Global thread local context remove interceptor class.
 * @since 2024-10-13 21:06
 */
@Component
@Slf4j
public class GlobalThreadLocalContextRemoveInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception exception) throws Exception {
        UserContext.removeUserId();
        UserContext.removeUserTempId();
        DebugTraceContext.removeTraceId();
        log.trace("User context removed.");
    }
}
