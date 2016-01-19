package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Common text resp-msg.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class RespText extends RespMsg {

    @Element(name = DOC_ELE_CONTENT, raw = false)
    private String mContent;

    public RespText() {
        this(null, null, null);
    }

    public RespText(String fromUser, String toUser, String content) {
        super(fromUser, toUser, MSG_TYPE_TEXT);
        setContent(content);
    }

    @Override
    public String toString() {
        return "RespText{" + super.toString() + ", mContent='" + mContent + "'}";
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
