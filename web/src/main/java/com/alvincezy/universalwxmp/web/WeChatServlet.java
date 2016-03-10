package com.alvincezy.universalwxmp.web;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.alvincezy.universalwxmp.generic.config.EncryptConfig;
import com.alvincezy.universalwxmp.generic.message.WXMsgs;
import com.alvincezy.universalwxmp.generic.utils.AesException;
import com.alvincezy.universalwxmp.generic.utils.SecurityHelper;
import com.alvincezy.universalwxmp.web.controller.WeChatCoreController;
import com.alvincezy.universalwxmp.web.util.ReqHelper;
import com.alvincezy.universalwxmp.web.util.RespHelper;
import com.alvincezy.universalwxmp.web.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "WechatOfficialServlet", urlPatterns = "/service", asyncSupported = true)
public class WeChatServlet extends HttpServlet {

    private static final String TAG = WeChatServlet.class.getName();

    @Override
    public void init() throws ServletException {
        getLogger().info("***service servlet init.***");
        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
        initEncryptConfig();
        getLogger().info("******use encrypt mode<{}> current.", EncryptConfig.getInstance().isEncypt());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getLogger().debug("***received get-req");

        EncryptConfig config = EncryptConfig.getInstance();
        String token = config.getToken();
        if (validateWechatReq(token, ReqHelper.getParam(req, "signature"),
                ReqHelper.getParam(req, "timestamp"), ReqHelper.getParam(req, "nonce"))) {
            getLogger().info("write resp echostr: " + ReqHelper.getParam(req, "echostr"));
            RespHelper.setContentType(resp, RespHelper.CONTENT_TYPE_DEF);
            RespHelper.writeResp(resp, ReqHelper.getParam(req, "echostr"));
        } else {
            getLogger().info("validate failure.");
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RespHelper.setContentType(resp, RespHelper.CONTENT_TYPE_DEF);
        WeChatCoreController controller = new WeChatCoreController();
        String respContent = controller.handleWxMessage(req, resp);
        if (!StringUtils.isEmpty(respContent)) {
            String finalResp = respContent;
            if (EncryptConfig.getInstance().isEncypt()) {
                try {
                    finalResp = WXMsgs.encryptRespMsg(respContent, Long.toString(TimeUtil.getTimestamp()), req.getParameter("nonce"));
                } catch (AesException e) {
                    getLogger().error(e.getMessage());
                    getLogger().trace(e.getMessage(), e);
                }
            }
            getLogger().debug("******reply message >>> {}", finalResp);
            RespHelper.writeResp(resp, finalResp);
        }
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
            digest = SecurityHelper.getSHA1Digest(s.toString()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        getLogger().debug("******validate wechat req - {} {} {} - {}", token, signature, timestamp, digest);

        return StringUtils.equals(signature, digest);
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(TAG);
    }

    /*
     * init wechat-mp encrypt config
     */
    private void initEncryptConfig() {
        ServletContext context = getServletContext();
        EncryptConfig config = (EncryptConfig) context.getAttribute(EncryptConfig.TAG);

        if (config == null) {
            config = EncryptConfig.getInstance();
            context.setAttribute(EncryptConfig.TAG, config);

            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(getClass().getResource("/wechat_official_config.properties").getPath()));
                config.set(properties);
            } catch (IOException e) {
                getLogger().trace(e.getMessage(), e);
            }
        }
    }
}
