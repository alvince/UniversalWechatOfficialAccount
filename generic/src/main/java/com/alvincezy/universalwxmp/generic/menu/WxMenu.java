package com.alvincezy.universalwxmp.generic.menu;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2016/1/11.
 *
 * @author yang.zhang@tyyouxi.com
 */
public abstract class WxMenu {

    public static final String TYPE_CLICK = "click";
    public static final String TYPE_SCAN = "scancode_push";
    public static final String TYPE_VIEW = "view";

    @JSONField(name = "name")
    protected String mName;

    public WxMenu(String name) {
        setName(name);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
