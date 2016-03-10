package com.alvincezy.universalwxmp.web.controller;

import com.alvincezy.universalwxmp.generic.WXEncryptReqBundle;
import com.alvincezy.universalwxmp.generic.config.EncryptConfig;
import com.alvincezy.universalwxmp.generic.message.WXMsgs;
import com.alvincezy.universalwxmp.generic.message.MsgWrapper;
import com.alvincezy.universalwxmp.generic.message.req.*;
import com.alvincezy.universalwxmp.generic.message.req.event.EventMsg;
import com.alvincezy.universalwxmp.generic.message.resp.RespText;
import com.alvincezy.universalwxmp.web.util.ReqHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WeChatCoreController {

    private static final String TAG = WeChatCoreController.class.getName();

    public String handleWxMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String respContent = null;

        WXEncryptReqBundle wxReqBundle = ReqHelper.buildReqBundle(request);
        getLogger().debug("post data: {}\n params: {}",
                wxReqBundle.getPostData(), readMapContent(request.getParameterMap()));

        MsgWrapper msgWrapper = null;
        try {
            msgWrapper = wrapMsg(wxReqBundle);
        } catch (ParserConfigurationException | SAXException e) {
            getLogger().trace(e.getMessage(), e);
        }

        if (msgWrapper != null) {
            switch (msgWrapper.type) {
                case EVENT:
                    respContent = handleEvent((EventMsg) msgWrapper.msg);
                    break;
                case IMAGE:
                    respContent = handleImage((ImageMsg) msgWrapper.msg);
                    break;
                case LINK:
                    respContent = handleLink((LinkMsg) msgWrapper.msg);
                    break;
                case LOCATION:
                    respContent = handleLocation((LocationMsg) msgWrapper.msg);
                    break;
                case SHORT_VIDEO:
                case VIDEO:
                    respContent = handleVideo((VideoMsg) msgWrapper.msg);
                    break;
                case VOICE:
                    respContent = handleVoice((VoiceMsg) msgWrapper.msg);
                    break;
                case TEXT:
                default:
                    respContent = handleText((TextMsg) msgWrapper.msg);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return respContent;
    }

    protected String handleEvent(EventMsg event) {
        return "";
    }

    protected String handleImage(ImageMsg msg) {
        return "";
    }

    protected String handleLink(LinkMsg msg) {
        return "";
    }

    protected String handleLocation(LocationMsg msg) {
        return "";
    }

    protected String handleVideo(VideoMsg msg) {
        return "";
    }

    protected String handleVoice(VoiceMsg msg) {
        return "";
    }

    protected String handleText(TextMsg msg) {
        RespText textMsg = new RespText();
        textMsg.setFromUser(msg.getToUser());
        textMsg.setToUser(msg.getFromUser());
        textMsg.setContent("Test text message!");
        MsgWrapper msgWrapper = new MsgWrapper(textMsg);
        return msgWrapper.transform2Xml();
    }

    private String read(BufferedReader reader) throws IOException {
        StringBuilder data = new StringBuilder();
        String input;
        while ((input = reader.readLine()) != null) {
            data.append(input);
        }
        return data.toString();
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger(TAG);
    }

    private String readMapContent(Map<String, String[]> map) {
        StringBuilder cache = new StringBuilder();
        for (Map.Entry<String, String[]> item : map.entrySet()) {
            cache.append(item.getKey()).append("=");
            String[] values = item.getValue();
            for (String value : values) {
                cache.append(value).append(",");
            }
        }
        return cache.toString();
    }

    private MsgWrapper wrapMsg(WXEncryptReqBundle reqBundle) throws IOException, ParserConfigurationException, SAXException {
        MsgWrapper msgWrapper;
        if (EncryptConfig.getInstance().isEncypt()) {
            msgWrapper = WXMsgs.parseEncrypt(reqBundle);
        } else {
            InputStream msgIS = new ByteArrayInputStream(reqBundle.getPostData().getBytes());
            msgWrapper = WXMsgs.parse(msgIS);
            msgIS.close();
        }
        getLogger().debug("******received message >>> {}", msgWrapper.msg);
        return msgWrapper;
    }
}
