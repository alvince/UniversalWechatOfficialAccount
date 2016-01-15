package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Voice message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class VoiceMsg extends MediaMsg {

    @Element(name = DOC_ELE_FORMAT)
    private String mFormat;

    @Element(name = DOC_ELE_RECOGNITION)
    private String mRecongnition;

    public VoiceMsg(long msgId, String fromUser, String toUser, long createTime, String mediaId, String format) {
        super(msgId, fromUser, toUser, MSG_TYPE_VOICE, createTime, mediaId);
        setFormat(format);
    }

    @Override
    public String toString() {
        return "VoiceMsg{" + super.toString() + ", Format='" + mFormat + "', Recongnition='" + mRecongnition + "'}";
    }

    public String getFormat() {
        return mFormat;
    }

    public String getRecongnition() {
        return mRecongnition;
    }

    public void setFormat(String format) {
        mFormat = format;
    }

    public void setRecongnition(String recongnition) {
        if (!StringUtils.isEmpty(recongnition)) {
            mRecongnition = recongnition;
        }
    }

    public boolean hasRecongnition() {
        return mRecongnition != null;
    }


    public static class Builder extends MediaMsg.Builder {
        private String format;
        private String recongnition;

        @Override
        public Builder attr(Map<String, WxNode> attrs) {
            if (attrs != null && !attrs.isEmpty()) {
                set(attrs);
                format = attrs.get(DOC_ELE_FORMAT).getValue();
                WxNode recon = attrs.get(DOC_ELE_RECOGNITION);
                if (recon != null) {
                    recongnition = recon.getValue();
                }
            }
            return this;
        }

        @Override
        public VoiceMsg build() {
            VoiceMsg msg = new VoiceMsg(msgId, from, to, createTime, mediaId, format);
            if (recongnition != null) {
                msg.setRecongnition(recongnition);
            }
            return msg;
        }
    }
}
