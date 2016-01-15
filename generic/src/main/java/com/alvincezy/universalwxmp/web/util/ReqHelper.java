package com.alvincezy.universalwxmp.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class ReqHelper {

    public static String getParam(ServletRequest req, String paramName) {
        String param = req.getParameter(paramName);
        return param == null ? StringUtils.EMPTY : param;
    }
}
