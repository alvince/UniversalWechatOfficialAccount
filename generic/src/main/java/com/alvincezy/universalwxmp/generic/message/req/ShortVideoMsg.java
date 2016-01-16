package com.alvincezy.universalwxmp.generic.message.req;

/**
 * Short-video message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class ShortVideoMsg extends VideoMsg {

    public ShortVideoMsg(long msgId, String fromUser, String toUser, long createTime, String mediaId, String thumbMediaId) {
        super(msgId, fromUser, toUser, createTime, mediaId, thumbMediaId);
        setMsgType(MSG_TYPE_VIDEO_SHORT);
    }


    public static class Builder extends VideoMsg.Builder {

        @Override
        public ShortVideoMsg build() {
            return new ShortVideoMsg(msgId, from, to, createTime, mediaId, thumbMediaId);
        }
    }
}
