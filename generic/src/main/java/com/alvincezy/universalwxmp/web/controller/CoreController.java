package com.alvincezy.universalwxmp.web.controller;

import com.alvincezy.universalwxmp.generic.message.WxMsgWrapper;
import com.alvincezy.universalwxmp.generic.message.req.*;
import com.alvincezy.universalwxmp.generic.message.req.event.EventMsg;
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
public class CoreController {

    public void handleWxMessage(HttpServletRequest request, HttpServletResponse response) {
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
            switch (msgWrapper.type) {
                case EVENT:
                    handleEvent((EventMsg) msgWrapper.msg);
                    break;
                case IMAGE:
                    handleImage((ImageMsg) msgWrapper.msg);
                    break;
                case LINK:
                    handleLink((LinkMsg) msgWrapper.msg);
                    break;
                case LOCATION:
                    handleLocation((LocationMsg) msgWrapper.msg);
                    break;
                case SHORT_VIDEO:
                case VIDEO:
                    handleVideo((VideoMsg) msgWrapper.msg);
                    break;
                case VOICE:
                    handleVoice((VoiceMsg) msgWrapper.msg);
                    break;
                case TEXT:
                default:
                    handleText((TextMsg) msgWrapper.msg);
            }
        }
    }

    protected void handleEvent(EventMsg event) {

    }

    protected void handleImage(ImageMsg msg) {

    }

    protected void handleLink(LinkMsg msg) {

    }

    protected void handleLocation(LocationMsg msg) {

    }

    protected void handleVideo(VideoMsg msg) {

    }

    protected void handleVoice(VoiceMsg msg) {

    }

    protected void handleText(TextMsg msg) {
    }
}
