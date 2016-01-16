package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.generic.message.WxMsg;
import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Message received from wx-server.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public abstract class RecMsg extends WxMsg {

    /**
     * Message type of link msg
     */
    public static final String MSG_TYPE_LINK = "link";

    /**
     * Message type of location msg
     */
    public static final String MSG_TYPE_LOCATION = "location";

    /**
     * Message type of short-video msg
     */
    public static final String MSG_TYPE_VIDEO_SHORT = "shortvideo";

    public RecMsg(long msgId, String fromUser, String toUser, String msgType, long createTime) {
        super(fromUser, toUser, msgType, createTime);
        setMsgId(msgId);
    }

    /**
     * Message id
     */
    @Element(name = DOC_ELE_MSG_ID)
    protected long mMsgId;

    public long getMsgId() {
        return mMsgId;
    }

    public void setMsgId(long msgId) {
        mMsgId = msgId;
    }

    @Override
    public void setMsgType(String msgType) {
        super.setMsgType(msgType);
    }

    @Override
    public String toString() {
        return super.toString() + ", MsgId=" + mMsgId;
    }


    protected static abstract class Builder extends WxMsg.Builder {

        protected long msgId;

        @Override
        protected void set(String attrName, XmlNode attr) {
            if (StringUtils.equals(DOC_ELE_MSG_ID, attrName)) {
                msgId = attr.getLong();
            } else {
                super.set(attrName, attr);
            }
        }
    }
}
