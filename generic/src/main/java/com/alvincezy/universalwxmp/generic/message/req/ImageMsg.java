package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

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
        public ImageMsg build() {
            return new ImageMsg(msgId, from, to, createTime, mediaId, picUrl);
        }

        @Override
        protected void set(String attrName, XmlNode attr) {
            if (StringUtils.equals(DOC_ELE_PIC_URL, attrName)) {
                picUrl = attr.getValue();
            }
            super.set(attrName, attr);
        }
    }
}
