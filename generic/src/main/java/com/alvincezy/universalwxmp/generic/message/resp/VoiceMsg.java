package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Voice resp-msg.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class VoiceMsg extends MediaMsg {

    @Element(name = DOC_ELE_VOICE)
    private Media mMedia;

    public VoiceMsg(String fromUser, String toUser, String mediaId) {
        super(fromUser, toUser, MSG_TYPE_VOICE, mediaId);
    }

    @Override
    protected Media getMedia(boolean instance) {
        if (mMedia == null && instance) {
            mMedia = new Media();
        }
        return mMedia;
    }
}
