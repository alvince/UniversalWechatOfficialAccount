package com.alvincezy.universalwxmp.generic.message;

import com.alvincezy.universalwxmp.generic.WXEncryptReqBundle;
import com.alvincezy.universalwxmp.generic.config.EncryptConfig;
import com.alvincezy.universalwxmp.generic.message.req.*;
import com.alvincezy.universalwxmp.generic.message.req.event.EventMsg;
import com.alvincezy.universalwxmp.generic.message.xml.WXNode;
import com.alvincezy.universalwxmp.generic.message.xml.WXXmlParseHandler;
import com.alvincezy.universalwxmp.generic.utils.AesException;
import com.alvincezy.universalwxmp.generic.utils.WXBizMsgCrypt;
import com.alvincezy.universalwxmp.util.xml.XmlNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 *
 * @author alvince.zy@gmail.com
 */
public class WXMsgs {

    private static final String TAG = WXMsgs.class.getName();
    private static SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
    private static WXBizMsgCrypt mWXBizMsgCrypt;

    /**
     * Parse to {@link MsgWrapper} from xml-input-stream.
     *
     * @param xmlStream message xml input stream
     * @return {@link MsgWrapper} msg
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static MsgWrapper parse(InputStream xmlStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = mSAXParserFactory.newSAXParser();
        WXXmlParseHandler handler = new WXXmlParseHandler();
        parser.parse(xmlStream, handler);

        WXNode root = handler.result();
        List<XmlNode> nodes = root.list();

        MsgWrapper wrapper = new MsgWrapper();
        parseMsg(root, nodes, wrapper);

        return wrapper;
    }

    public static MsgWrapper parseEncrypt(WXEncryptReqBundle params)
            throws IOException, SAXException, ParserConfigurationException {
        String msgContent = null;
        try {
            msgContent = getWXBizMsgCrypt().decryptMsg(
                    params.getMsgSignature(), params.getTimestamp(), params.getNonce(), params.getPostData());
        } catch (AesException e) {
            getLogger().trace(e.getMessage(), e);
        }

        MsgWrapper wrapper = null;
        if (!StringUtils.isEmpty(msgContent)) {
            InputStream msgIS = new ByteArrayInputStream(msgContent.getBytes());
            wrapper = parse(msgIS);
            msgIS.close();
        }
        return wrapper;
    }

    /**
     * Encrypt reply message by {@link WXBizMsgCrypt}
     *
     * @throws AesException
     * @see WXBizMsgCrypt#encryptMsg(String, String, String)
     */
    public static String encryptRespMsg(String replyMsg, String timestamp, String nonce) throws AesException {
        return getWXBizMsgCrypt().encryptMsg(replyMsg, timestamp, nonce);
    }

    private static WXBizMsgCrypt getWXBizMsgCrypt() {
        if (mWXBizMsgCrypt == null) {
            EncryptConfig config = EncryptConfig.getInstance();
            try {
                mWXBizMsgCrypt = new WXBizMsgCrypt(config.getToken(), config.getEncodingKey(), config.getAppId());
            } catch (AesException e) {
                LoggerFactory.getLogger(TAG).error(e.getMessage());
                throw new IllegalStateException("Wechat encrypt config error: encodingKey size is not 43.");
            }
        }
        return mWXBizMsgCrypt;
    }

    private static void parseMsg(WXNode root, List<XmlNode> nodes, MsgWrapper wrapper) {
        String msgType = root.getType().getValue();
        if (StringUtils.equals(WXMsg.MSG_TYPE_IMAGE, msgType)) {
            wrapper.type = MsgWrapper.Type.IMAGE;
            wrapper.msg = new ImageMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_LINK, msgType)) {
            wrapper.type = MsgWrapper.Type.LINK;
            wrapper.msg = new LinkMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_LOCATION, msgType)) {
            wrapper.type = MsgWrapper.Type.LOCATION;
            wrapper.msg = new LocationMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_TEXT, msgType)) {
            wrapper.type = MsgWrapper.Type.TEXT;
            wrapper.msg = new TextMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_VIDEO, msgType)) {
            wrapper.type = MsgWrapper.Type.VIDEO;
            wrapper.msg = new VideoMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_VIDEO_SHORT, msgType)) {
            wrapper.type = MsgWrapper.Type.SHORT_VIDEO;
            wrapper.msg = new ShortVideoMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_VOICE, msgType)) {
            wrapper.type = MsgWrapper.Type.VOICE;
            wrapper.msg = new VoiceMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_EVENT, msgType)) {
            wrapper.type = MsgWrapper.Type.EVENT;
            wrapper.msg = new EventMsg.Builder().attr(nodes).build();
        }
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(WXMsgs.class);
    }
}
