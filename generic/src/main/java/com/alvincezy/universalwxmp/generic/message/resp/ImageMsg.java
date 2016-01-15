package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Image resp-msg.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class ImageMsg extends MediaMsg {

    @Element(name = DOC_ELE_IMAGE)
    private Media mMedia;

    public ImageMsg(String fromUser, String toUser, String mediaId) {
        super(fromUser, toUser, MSG_TYPE_IMAGE, mediaId);
    }

    @Override
    protected Media getMedia(boolean instance) {
        if (mMedia == null && instance) {
            mMedia = new Media();
        }
        return mMedia;
    }
}
