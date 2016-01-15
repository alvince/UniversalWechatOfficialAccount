package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Video resp-msg.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class VideoMsg extends MediaMsg {

    @Element(name = DOC_ELE_VIDEO)
    protected Video mMedia;

    public VideoMsg() {
        this(null, null, null, null, null);
    }

    public VideoMsg(String fromUser, String toUser, String mediaId, String title, String description) {
        super(fromUser, toUser, MSG_TYPE_VIDEO, mediaId);
        setTitle(title);
        setDescription(description);
    }

    public String getDescription() {
        return mMedia == null ? StringUtils.EMPTY : mMedia.getDescription();
    }

    public String getTitle() {
        return mMedia == null ? StringUtils.EMPTY : mMedia.getTitle();
    }

    public void setDescription(String description) {
        if (!StringUtils.isEmpty(description)) {
            getMedia(true).setDescription(description);
        }
    }

    public void setTitle(String title) {
        if (!StringUtils.isEmpty(title)) {
            getMedia(true).setTitle(title);
        }
    }

    protected Video getMedia(boolean instance) {
        if (mMedia == null && instance) {
            mMedia = new Video();
        }
        return mMedia;
    }


    static class Video extends Media {

        @Element(name = DOC_ELE_DESCRIPTION, raw = false)
        private String mDesc;

        @Element(name = DOC_ELE_TITLE, raw = false)
        private String mTitle;

        public String getDescription() {
            return StringUtilsExtra.var(mDesc);
        }

        public String getTitle() {
            return StringUtilsExtra.var(mTitle);
        }

        public void setDescription(String description) {
            mDesc = description;
        }

        public void setTitle(String title) {
            mTitle = title;
        }
    }
}
