package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class RespMusic extends RespMsg {

    private static final String DOC_ELE_MUSIC_URL = "MusicUrl";
    private static final String DOC_ELE_HQ_MUSIC_URL = "HqMusicUrl";

    @Element(name = DOC_ELE_MUSIC)
    private Music mMusic;

    public RespMusic() {
        this(null, null, null, null, null);
    }

    public RespMusic(String fromUser, String toUser, String musicUrl, String hqMusicUrl, String thumbMediaId) {
        this(fromUser, toUser, null, null, musicUrl, hqMusicUrl, thumbMediaId);
    }

    public RespMusic(String fromUser, String toUser, String title, String desc, String musicUrl, String hqMusicUrl, String thumbMediaId) {
        super(fromUser, toUser, MSG_TYPE_MUSIC);
        setTitle(title);
        setDescription(desc);
        setMusicUrl(musicUrl);
        setHqMusicUrl(hqMusicUrl);
        setThumbMediaId(thumbMediaId);
    }

    public String getDescription() {
        return mMusic == null ? StringUtils.EMPTY : mMusic.getDescription();
    }

    public String getTitle() {
        return mMusic == null ? StringUtils.EMPTY : mMusic.getTitle();
    }

    public String getMusicUrl() {
        return mMusic == null ? StringUtils.EMPTY : mMusic.getMusicUrl();
    }

    public String getHqMusicUrl() {
        return mMusic == null ? StringUtils.EMPTY : mMusic.getHqMusicUrl();
    }

    public String getThumbMediaId() {
        return mMusic == null ? StringUtils.EMPTY : mMusic.getThumbMediaId();
    }

    public void setDescription(String description) {
        if (!StringUtils.isEmpty(description)) {
            getMusic().setDescription(description);
        }
    }

    public void setTitle(String title) {
        if (!StringUtils.isEmpty(title)) {
            getMusic().setTitle(title);
        }
    }

    public void setMusicUrl(String musicUrl) {
        if (!StringUtils.isEmpty(musicUrl)) {
            getMusic().setMusicUrl(musicUrl);
        }
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        if (!StringUtils.isEmpty(hqMusicUrl)) {
            getMusic().setHqMusicUrl(hqMusicUrl);
        }
    }

    public void setThumbMediaId(String thumbMediaId) {
        if (!StringUtils.isEmpty(thumbMediaId)) {
            getMusic().setThumbMediaId(thumbMediaId);
        }
    }

    private Music getMusic() {
        if (mMusic == null) {
            mMusic = new Music();
        }
        return mMusic;
    }


    private class Music {

        @Element(name = DOC_ELE_DESCRIPTION, raw = false)
        private String mDesc;

        @Element(name = DOC_ELE_TITLE, raw = false)
        private String mTitle;

        @Element(name = DOC_ELE_MUSIC_URL, raw = false)
        private String mMusicUrl;

        @Element(name = DOC_ELE_HQ_MUSIC_URL, raw = false)
        private String mHqMusicUrl;

        @Element(name = DOC_ELE_THUMB_MEDIA_ID, raw = false)
        private String mThumbMediaId;

        public String getDescription() {
            return StringUtilsExtra.var(mDesc);
        }

        public String getTitle() {
            return StringUtilsExtra.var(mTitle);
        }

        public String getMusicUrl() {
            return StringUtilsExtra.var(mMusicUrl);
        }

        public String getHqMusicUrl() {
            return StringUtilsExtra.var(mHqMusicUrl);
        }

        public String getThumbMediaId() {
            return StringUtilsExtra.var(mThumbMediaId);
        }

        public void setDescription(String description) {
            mDesc = description;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public void setMusicUrl(String musicUrl) {
            mMusicUrl = musicUrl;
        }

        public void setHqMusicUrl(String hqMusicUrl) {
            mHqMusicUrl = hqMusicUrl;
        }

        public void setThumbMediaId(String thumbMediaId) {
            mThumbMediaId = thumbMediaId;
        }
    }
}
