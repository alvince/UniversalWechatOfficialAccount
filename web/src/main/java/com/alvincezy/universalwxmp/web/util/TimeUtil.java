package com.alvincezy.universalwxmp.web.util;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/3/3.
 *
 * @author alvince.zy@gmail.com
 */
public class TimeUtil {

    public static long getTimestamp() {
        Calendar time = Calendar.getInstance();
        int zoneOffset = time.get(Calendar.ZONE_OFFSET);
        int dstOffset = time.get(Calendar.DST_OFFSET);
        time.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return time.getTimeInMillis();
    }
}
