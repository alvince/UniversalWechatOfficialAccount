package com.alvincezy.universalwxmp.web.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class RespHelper {

    public static final String CONTENT_TYPE_DEF = "text/plain; charset=UTF-8";

    public static void setContentType(HttpServletResponse resp, String contentType) {
        resp.setContentType(contentType);
    }

    public static void writeResp(HttpServletResponse resp, String content) throws IOException {
        Writer out = resp.getWriter();
        out.write(content);
        out.flush();
        out.close();
    }
}
