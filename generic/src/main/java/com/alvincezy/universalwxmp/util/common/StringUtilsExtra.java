package com.alvincezy.universalwxmp.util.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/13.
 *
 * @author alvince.zy@gmail.com
 */
public class StringUtilsExtra {

    public static boolean isNull(String str) {
        return StringUtils.equals("NULL", str.toUpperCase());
    }

    public static String var(String str) {
        return StringUtils.isEmpty(str) ? StringUtils.EMPTY : str;
    }

    public static String varWithoutNull(String str) {
        String text = var(str);
        return isNull(text) ? StringUtils.EMPTY : text;
    }
}
