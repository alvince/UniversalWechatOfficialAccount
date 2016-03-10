package com.alvincezy.universalwxmp.web.util;

import com.alvincezy.universalwxmp.generic.WXEncryptReqBundle;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

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

    public static WXEncryptReqBundle buildReqBundle(ServletRequest req) throws IOException {
        WXEncryptReqBundle bundle = new WXEncryptReqBundle(req.getParameter(WXEncryptReqBundle.PARAM_NONCE),
                req.getParameter(WXEncryptReqBundle.PARAM_MSG_SIGNATURE), req.getParameter(WXEncryptReqBundle.PARAM_TIMESTAMP));

        BufferedReader reqReader = req.getReader();
        StringBuilder postData = new StringBuilder();
        String input;
        while ((input = reqReader.readLine()) != null) {
            postData.append(input);
        }
        bundle.setPostData(postData.toString());

        return bundle;
    }
}
