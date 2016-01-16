package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public abstract class MediaMsg extends RecMsg {

    @Element(name = DOC_ELE_MEDIA_ID)
    protected String mMediaId;

    public MediaMsg(long msgId, String fromUser, String toUser, String msgType, long createTime, String mediaId) {
        super(msgId, fromUser, toUser, msgType, createTime);
        setMediaId(mediaId);
    }

    public String getMediaId() {
        return mMediaId;
    }

    public void setMediaId(String mediaId) {
        mMediaId = mediaId;
    }

    @Override
    public String toString() {
        return super.toString() + ", MediaId='" + mMediaId + '\'';
    }


    protected static abstract class Builder extends RecMsg.Builder {

        protected String mediaId;

        @Override
        protected void set(String attrName, XmlNode attr) {
            if (StringUtils.equals(DOC_ELE_MEDIA_ID, attrName)) {
                mediaId = attr.getValue();
            } else {
                super.set(attrName, attr);
            }
        }
    }
}
