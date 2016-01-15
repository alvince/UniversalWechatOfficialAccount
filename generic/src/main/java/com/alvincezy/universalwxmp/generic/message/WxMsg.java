package com.alvincezy.universalwxmp.generic.message;

import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Wx-mp message.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
@Element(name = WxMsg.DOC_ELE_ROOT)
public abstract class WxMsg {

    public static final String DOC_ELE_CREATE_TIME = "CreateTime";
    public static final String DOC_ELE_CONTENT = "Content";
    public static final String DOC_ELE_DESCRIPTION = "Description";
    public static final String DOC_ELE_FORMAT = "Format";
    public static final String DOC_ELE_FROM_USER_NAME = "FromUserName";
    public static final String DOC_ELE_LABEL = "Label";
    public static final String DOC_ELE_LOCATION_X = "Location_X";
    public static final String DOC_ELE_LOCATION_Y = "Location_Y";
    public static final String DOC_ELE_MSG_ID = "MsgId";
    public static final String DOC_ELE_MEDIA_ID = "MediaId";
    public static final String DOC_ELE_MSG_TYPE = "MsgType";
    public static final String DOC_ELE_PIC_URL = "PicUrl";
    public static final String DOC_ELE_RECOGNITION = "Recognition";
    public static final String DOC_ELE_ROOT = "xml";
    public static final String DOC_ELE_SCALE = "Scale";
    public static final String DOC_ELE_THUMB_MEDIA_ID = "ThumbMediaId";
    public static final String DOC_ELE_TITLE = "Title";
    public static final String DOC_ELE_TO_USER_NAME = "ToUserName";
    public static final String DOC_ELE_URL = "Url";

    /**
     * Message type of image msg
     */
    public static final String MSG_TYPE_IMAGE = "image";

    /**
     * Message type of common text msg
     */
    public static final String MSG_TYPE_TEXT = "text";

    /**
     * Message type of video msg
     */
    public static final String MSG_TYPE_VIDEO = "video";

    /**
     * Message type of voice msg
     */
    public static final String MSG_TYPE_VOICE = "voice";

    /**
     * User message send to
     */
    @Element(name = DOC_ELE_TO_USER_NAME, raw = false)
    protected String mToUser;

    /**
     * User message from
     */
    @Element(name = DOC_ELE_FROM_USER_NAME, raw = false)
    protected String mFromUser;

    /**
     * Message type from
     */
    @Element(name = DOC_ELE_MSG_TYPE, raw = false)
    protected String mMsgType;

    /**
     * Message create time
     */
    @Element(name = DOC_ELE_CREATE_TIME)
    protected long mCreateTime;

    public WxMsg(String fromUser, String toUser, String msgType, long createTime) {
        setFromUser(fromUser);
        setToUser(toUser);
        setMsgType(msgType);
        setCreateTime(createTime);
    }

    public String getToUser() {
        return StringUtilsExtra.var(mToUser);
    }

    public String getFromUser() {
        return StringUtilsExtra.var(mFromUser);
    }

    public String getMsgType() {
        return StringUtilsExtra.var(mMsgType);
    }

    public long getCreateTime() {
        return mCreateTime;
    }

    public void setToUser(String toUser) {
        if (!StringUtils.isEmpty(toUser)) {
            mToUser = toUser;
        }
    }

    public void setFromUser(String fromUser) {
        if (!StringUtils.isEmpty(fromUser)) {
            mFromUser = fromUser;
        }
    }

    public void setCreateTime(long createTime) {
        mCreateTime = createTime > 0 ? createTime : 0;
    }

    protected void setMsgType(String msgType) {
        mMsgType = msgType;
    }

    @Override
    public String toString() {
        return "FromUser='" + mFromUser + "', ToUser='" + mToUser + "', MsgType='" + mMsgType + "', CreateTime=" + mCreateTime;
    }


    protected static abstract class Builder {

        protected String from;
        protected String to;
        protected long createTime;

        public abstract Builder attr(Map<String, WxNode> attrs);

        public abstract WxMsg build();

        protected void set(Map<String, WxNode> attrs) {
            from = attrs.get(WxMsg.DOC_ELE_FROM_USER_NAME).getValue();
            to = attrs.get(WxMsg.DOC_ELE_TO_USER_NAME).getValue();
            createTime = attrs.get(WxMsg.DOC_ELE_CREATE_TIME).getLong();
        }
    }
}
