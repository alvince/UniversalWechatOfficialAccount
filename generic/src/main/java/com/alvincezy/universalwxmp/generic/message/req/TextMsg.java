package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Common Text message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class TextMsg extends RecMsg {

    @Element(name = DOC_ELE_CONTENT)
    private String mContent;

    public TextMsg(long msgId, String fromUser, String toUser, long createTime, String content) {
        super(msgId, fromUser, toUser, MSG_TYPE_TEXT, createTime);
        setContent(content);
    }

    @Override
    public String toString() {
        return "TextMsg{" + super.toString() + ", Content='" + mContent + "'}";
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }


    public static class Builder extends RecMsg.Builder {

        private String content;

        @Override
        public TextMsg build() {
            return new TextMsg(msgId, from, to, createTime, content);
        }

        @Override
        protected void set(String attrName, XmlNode attr) {
            if (StringUtils.equals(DOC_ELE_CONTENT, attrName)) {
                content = attr.getValue();
            } else {
                super.set(attrName, attr);
            }
        }
    }
}
