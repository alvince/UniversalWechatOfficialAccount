package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Video message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class VideoMsg extends MediaMsg {

    @Element(name = DOC_ELE_THUMB_MEDIA_ID)
    protected String mThumbMediaId;

    public VideoMsg(long msgId, String fromUser, String toUser, long createTime, String mediaId, String thumbMediaId) {
        super(msgId, fromUser, toUser, MSG_TYPE_VIDEO, createTime, mediaId);
        setThumbMediaId(thumbMediaId);
    }

    @Override
    public String toString() {
        return "VideoMsg{" + super.toString() + ", ThumbMediaId='" + mThumbMediaId + "'}";
    }

    public String getThumbMediaId() {
        return mThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        mThumbMediaId = thumbMediaId;
    }
    
    
    public static class Builder extends MediaMsg.Builder {

        protected String thumbMediaId;

        @Override
        public VideoMsg build() {
            return new VideoMsg(msgId, from, to, createTime, mediaId, thumbMediaId);
        }

        @Override
        protected void set(String attrName, XmlNode attr) {
            if (StringUtils.equals(DOC_ELE_THUMB_MEDIA_ID, attrName)) {
                thumbMediaId = attr.getValue();
            } else {
                super.set(attrName, attr);
            }
        }
    }
}
