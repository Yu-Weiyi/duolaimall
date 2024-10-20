package pers.wayease.duolaimall.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.util
 * @name TimeUtil
 * @description Time util class.
 * @since 2024-10-20 12:14
 */
public class TimeUtil {

    public static Date changeToCST(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 8);
        return calendar.getTime();
    }
}
