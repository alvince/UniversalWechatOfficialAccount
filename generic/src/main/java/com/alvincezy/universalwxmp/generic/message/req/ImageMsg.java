package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;

import java.util.Map;

/**
 * Image message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class ImageMsg extends MediaMsg {

    @Element(name = DOC_ELE_PIC_URL)
    private String mPicUrl;

    public ImageMsg(long msgId, String fromUser, String toUser, long createTime, String mediaId, String picurl) {
        super(msgId, fromUser, toUser, MSG_TYPE_IMAGE, createTime, mediaId);
        setPicUrl(picurl);
    }

    @Override
    public String toString() {
        return "ImageMsg{" + super.toString() + ", PicUrl='" + mPicUrl + "'}";
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public void setPicUrl(String picUrl) {
        mPicUrl = picUrl;
    }


    public static class Builder extends MediaMsg.Builder {

        private String picUrl;

        @Override
        public Builder attr(Map<String, WxNode> attrs) {
            if (attrs != null && !attrs.isEmpty()) {
                set(attrs);
                picUrl = attrs.get(DOC_ELE_PIC_URL).getValue();
            }
            return this;
        }

        @Override
        public ImageMsg build() {
            return new ImageMsg(msgId, from, to, createTime, mediaId, picUrl);
        }
    }
}
