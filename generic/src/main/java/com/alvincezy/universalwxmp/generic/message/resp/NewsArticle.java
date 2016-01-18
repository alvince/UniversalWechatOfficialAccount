package com.alvincezy.universalwxmp.generic.message.resp;

import com.alvincezy.universalwxmp.generic.message.WxMsg;
import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
@Element(embed = true)
public class NewsArticle {

    @Element(name = WxMsg.DOC_ELE_DESCRIPTION, raw = false)
    protected String mDesc;

    @Element(name = WxMsg.DOC_ELE_TITLE, raw = false)
    protected String mTitle;

    @Element(name = WxMsg.DOC_ELE_PIC_URL, raw = false)
    protected String mPicUrl;

    @Element(name = WxMsg.DOC_ELE_URL, raw = false)
    protected String mUrl;

    public NewsArticle() {
        this(null, null, null, null);
    }

    public NewsArticle(String desc, String title, String picUrl, String url) {
        setDescription(desc);
        setTitle(title);
        setPicUrl(picUrl);
        setUrl(url);
    }

    public String getDescription() {
        return StringUtilsExtra.var(mDesc);
    }

    public String getPicUrl() {
        return StringUtilsExtra.var(mPicUrl);
    }

    public String getTitle() {
        return StringUtilsExtra.var(mTitle);
    }

    public String getUrl() {
        return StringUtilsExtra.var(mUrl);
    }

    public void setDescription(String description) {
        if (!StringUtils.isEmpty(description)) {
            mDesc = description;
        }
    }

    public void setPicUrl(String picUrl) {
        if (!StringUtils.isEmpty(picUrl)) {
            mPicUrl = picUrl;
        }
    }

    public void setTitle(String title) {
        if (!StringUtils.isEmpty(title)) {
            mTitle = title;
        }
    }

    public void setUrl(String url) {
        if (!StringUtils.isEmpty(url)) {
            mUrl = url;
        }
    }
}
