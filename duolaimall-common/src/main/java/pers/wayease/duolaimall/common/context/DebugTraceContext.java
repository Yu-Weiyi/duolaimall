package pers.wayease.duolaimall.common.context;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.context
 * @name DebugTraceContext
 * @description Debug trace context class.
 * @since 2024-10-20 22:00
 */
public class DebugTraceContext {

    private static ThreadLocal<Integer> traceId = ThreadLocal.withInitial(() -> 0);

    public static String getNextStyledTraceId() {
        traceId.set(traceId.get() + 1);
        return "[trace ID = " + traceId.get() + "]";
    }

    public static void removeTraceId() {
        traceId.remove();
    }
}
