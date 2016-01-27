package com.alvincezy.universalwxmp.web.controller;

import com.alvincezy.universalwxmp.generic.message.WxMsgWrapper;
import com.alvincezy.universalwxmp.generic.message.req.*;
import com.alvincezy.universalwxmp.generic.message.req.event.EventMsg;
import com.alvincezy.universalwxmp.generic.message.resp.RespText;
import com.alvincezy.universalwxmp.web.util.RespHelper;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WeChatCoreController {

    public void handleWxMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WxMsgWrapper msgWrapper = null;
        try {
            msgWrapper = WxMsgWrapper.parseXml(request.getInputStream());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (msgWrapper != null) {
            String respResult;
            switch (msgWrapper.type) {
                case EVENT:
                    respResult = handleEvent((EventMsg) msgWrapper.msg);
                    break;
                case IMAGE:
                    respResult = handleImage((ImageMsg) msgWrapper.msg);
                    break;
                case LINK:
                    respResult = handleLink((LinkMsg) msgWrapper.msg);
                    break;
                case LOCATION:
                    respResult = handleLocation((LocationMsg) msgWrapper.msg);
                    break;
                case SHORT_VIDEO:
                case VIDEO:
                    respResult = handleVideo((VideoMsg) msgWrapper.msg);
                    break;
                case VOICE:
                    respResult = handleVoice((VoiceMsg) msgWrapper.msg);
                    break;
                case TEXT:
                default:
                    respResult = handleText((TextMsg) msgWrapper.msg);
            }
            RespHelper.writeResp(response, respResult);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
        WxMsgWrapper msgWrapper = new WxMsgWrapper(textMsg);
        return msgWrapper.transform2Xml();
    }
}
