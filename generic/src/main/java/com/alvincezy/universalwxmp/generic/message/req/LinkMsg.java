package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Link message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class LinkMsg extends RecMsg {

    @Element(name = DOC_ELE_TITLE)
    private String mTitle;

    @Element(name = DOC_ELE_URL)
    private String mUrl;

    @Element(name = DOC_ELE_DESCRIPTION)
    private String mDesc;

    public LinkMsg(long msgId, String fromUser, String toUser, long createTime, String title, String url, String description) {
        super(msgId, fromUser, toUser, MSG_TYPE_LINK, createTime);
        setTitle(title);
        setUrl(url);
        setDescription(description);
    }

    @Override
    public String toString() {
        return "LinkMsg{" + super.toString() + ", Title='" + mTitle + "', Url='" + mUrl + "', Desc='" + mDesc + "'}";
    }

    public String getDescription() {
        return mDesc;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setDescription(String description) {
        mDesc = description;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setUrl(String url) {
        mUrl = url;
    }


    public static class Builder extends RecMsg.Builder {
        private String title;
        private String url;
        private String desc;

        @Override
        public LinkMsg build() {
            return new LinkMsg(msgId, from, to, createTime, title, url, desc);
        }

        @Override
        protected void set(String attrName, XmlNode attr) {
            if (StringUtils.equals(DOC_ELE_TITLE, attrName)) {
                title = attr.getValue();
            } else if (StringUtils.equals(DOC_ELE_URL, attrName)) {
                url = attr.getValue();
            } else if (StringUtils.equals(DOC_ELE_DESCRIPTION, attrName)) {
                desc = attr.getValue();
            } else {
                super.set(attrName, attr);
            }
        }
    }
}
