package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.generic.message.WXMsg;

import java.util.Calendar;
import java.util.Locale;

/**
 * Message response to wx-server(user).
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public abstract class RespMsg extends WXMsg {

    public static final String DOC_ELE_ARTICLES = "Articles";
    public static final String DOC_ELE_ARTICLE_COUNT = "ArticleCount";
    public static final String DOC_ELE_IMAGE = "Image";
    public static final String DOC_ELE_MUSIC = "Music";
    public static final String DOC_ELE_VIDEO = "Video";
    public static final String DOC_ELE_VOICE = "Voice";

    /**
     * Message type of music msg
     */
    public static final String MSG_TYPE_MUSIC = "music";

    /**
     * Message type of news msg
     */
    public static final String MSG_TYPE_NEWS = "news";

    public RespMsg(String fromUser, String toUser, String msgType) {
        super(fromUser, toUser, msgType, Calendar.getInstance(Locale.CHINA).getTimeInMillis());
    }
}
