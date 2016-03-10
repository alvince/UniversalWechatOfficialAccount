package com.alvincezy.universalwxmp.web.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/1.
 *
 * @author alvince.zy@gmail.com
 */
@WebFilter(servletNames = { "WechatOfficialServlet" }, filterName = "EncodingFilter", asyncSupported = true)
public class EncodingFilter implements Filter {

    private static final String ENCODING_DEF = "UTF-8";

    private String mEncoding;

    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (!StringUtils.isEmpty(encodingParam)) {
            mEncoding = encodingParam;
        } else {
            mEncoding = ENCODING_DEF;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(mEncoding);
        chain.doFilter(request, response);
    }

    public void destroy() {
        // do nothing
    }
}
