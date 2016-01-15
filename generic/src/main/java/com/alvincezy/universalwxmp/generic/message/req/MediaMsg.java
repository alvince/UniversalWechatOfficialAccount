package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;

import java.util.Map;

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
        protected void set(Map<String, WxNode> attrs) {
            super.set(attrs);
            mediaId = attrs.get(DOC_ELE_MEDIA_ID).getValue();
        }
    }
}
