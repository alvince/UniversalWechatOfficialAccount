package com.alvincezy.universalwxmp.web;

import com.alvincezy.universalwxmp.generic.config.GenericConfig;
import com.alvincezy.universalwxmp.generic.utils.SecurityHelper;
import com.alvincezy.universalwxmp.web.controller.CoreController;
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
public class ServiceServlet extends HttpServlet {

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
                for (Object key : properties.keySet()) {
                    String k = (String) key;
                    config.put(k, properties.getProperty(k));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenericConfig config = (GenericConfig) getServletContext().getAttribute(GenericConfig.TAG);
        String token = (String) config.get(GenericConfig.CONF_WECHAT_OFFICIAL_TOKEN);
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
        CoreController controller = new CoreController();
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

        return StringUtils.equals(signature, digest);
    }
}
