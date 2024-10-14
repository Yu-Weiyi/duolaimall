package pers.wayease.duolaimall.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.wayease.duolaimall.common.constant.HeaderConstant;
import pers.wayease.duolaimall.common.context.UserContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.interceptor
 * @name RequestHeaderInterceptor
 * @description Request header interceptor class.
 * @since 2024-10-13 15:03
 */
@Component
@Slf4j
public class RequestHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.info("JWT auth interceptor.");
        if (handler instanceof HandlerMethod) {
            return true;
        }

        //FIXME error in istio ext auth
        Long userId = Long.valueOf(httpServletRequest.getHeader(HeaderConstant.USER_ID_HEADER));
        if (userId == null || userId <= 0) {
            log.warn("Got user ID {} from header {}", userId, HeaderConstant.USER_ID_HEADER);

            // temp fix
            userId = 2L;// temp fix
//            return false;// temp fix
            // temp fix
        }
        UserContext.setUserId(userId);
        return true;
    }
}
