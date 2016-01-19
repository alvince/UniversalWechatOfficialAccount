package com.alvincezy.universalwxmp.web;

import com.alvincezy.universalwxmp.generic.config.GenericConfig;
import com.alvincezy.universalwxmp.generic.utils.SecurityHelper;
import com.alvincezy.universalwxmp.web.controller.WeChatCoreController;
import com.alvincezy.universalwxmp.web.util.ReqHelper;
import com.alvincezy.universalwxmp.web.util.RespHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WeChatServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        GenericConfig config = (GenericConfig) context.getAttribute(GenericConfig.TAG);
        if (config == null) {
            config = GenericConfig.getInstance();
            context.setAttribute(GenericConfig.TAG, config);

            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(getClass().getResource("/wechat_official_generic_config.properties").getPath()));
                config.set(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenericConfig config = (GenericConfig) getServletContext().getAttribute(GenericConfig.TAG);
        String token = config.getWeChatToken();
        boolean validate = validateWechatReq(token, ReqHelper.getParam(req, "signature"),
                ReqHelper.getParam(req, "timestamp"), ReqHelper.getParam(req, "nonce"));
        if (validate) {
            RespHelper.writeResp(resp, ReqHelper.getParam(req, "echostr"));
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RespHelper.setContentType(resp, RespHelper.CONTENT_TYPE_DEF);
        WeChatCoreController controller = new WeChatCoreController();
        controller.handleWxMessage(req, resp);
    }

    protected boolean validateWechatReq(String token, String signature, String timestamp, String nonce) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            return false;
        }

        String[] tmpArr = new String[]{token, timestamp, nonce};
        Arrays.sort(tmpArr);
        StringBuilder s = new StringBuilder();
        for (String str : tmpArr) {
            s.append(str);
        }

        String digest = null;
        try {
            digest = SecurityHelper.getSHA1Digest(s.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(digest);

        return StringUtils.equalsIgnoreCase(signature, digest);
    }
}
