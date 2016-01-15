package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.generic.message.WxMsg;
import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;

import java.util.Map;

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
        public Builder attr(Map<String, WxNode> attrs) {
            if (attrs != null && !attrs.isEmpty()) {
                set(attrs);
                content = attrs.get(WxMsg.DOC_ELE_CONTENT).getValue();
            }
            return this;
        }

        @Override
        public TextMsg build() {
            return new TextMsg(msgId, from, to, createTime, content);
        }
    }
}
