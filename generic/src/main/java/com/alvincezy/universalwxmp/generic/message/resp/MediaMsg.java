package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public abstract class MediaMsg extends RespMsg {

    public MediaMsg(String fromUser, String toUser, String msgType, String mediaId) {
        super(fromUser, toUser, msgType);
        setMediaId(mediaId);
    }

    public String getMediaId() {
        Media media = getMedia(false);
        return media == null ? StringUtils.EMPTY : media.getMediaId();
    }

    public void setMediaId(String mediaId) {
        if (!StringUtils.isEmpty(mediaId)) {
            getMedia(true).setMediaId(mediaId);
        }
    }

    protected abstract Media getMedia(boolean instance);

    /**
     * Media field define.
     */
    static class Media {

        @Element(name = DOC_ELE_MEDIA_ID, raw = false)
        protected String mMediaId;

        public String getMediaId() {
            return StringUtilsExtra.var(mMediaId);
        }

        public void setMediaId(String mediaId) {
            mMediaId = mediaId;
        }
    }
}
