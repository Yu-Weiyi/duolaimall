package pers.wayease.duolaimall.common.context;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.context
 * @name UserContext
 * @description User context class.
 * @since 2024-10-12 22:26
 */
@Data
@Slf4j
public class UserContext {

    private static ThreadLocal<Long> userId = ThreadLocal.withInitial(() -> 0L);
    private static ThreadLocal<String> userTempId = ThreadLocal.withInitial(() -> "");

    public static void setUserId(Long id) {
        userId.set(id);
        log.info("User context set user id={}.", id);
    }

    public static void setUserTempId(String id) {
        userTempId.set(id);
        log.info("User context set user temp id={}.", id);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static String getUserTempId() {
        return userTempId.get();
    }

    public static boolean isLogined() {
        return getUserId() != null && getUserId() != 0L;
    }

    public static String getCartUserId() {
        String cartUserId = isLogined() ? getUserId().toString() : getUserTempId();
        log.info("User context get cart user id of {}.", cartUserId);
        return cartUserId;
    }

    public static void removeUserId() {
        userId.remove();
    }

    public static void removeUserTempId() {
        userTempId.remove();
    }
}