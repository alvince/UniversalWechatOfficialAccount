package com.alvincezy.universalwxmp.generic.menu;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * View mType menu button.
 * <p/>
 * Created by Administrator on 2016/1/11.
 *
 * @author yang.zhang@tyyouxi.com
 */
public class ViewButton extends SubButton {

    @JSONField(name = "url")
    private String mUrl;

    public ViewButton(String name, String url) {
        super(name, TYPE_VIEW);
        setUrl(url);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
