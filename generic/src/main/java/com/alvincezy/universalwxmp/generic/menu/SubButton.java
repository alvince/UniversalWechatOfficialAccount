package com.alvincezy.universalwxmp.generic.menu;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Abstract sub menu button.
 * <p/>
 * Created by Administrator on 2016/1/11.
 *
 * @author yang.zhang@tyyouxi.com
 */
public abstract class SubButton extends WXMenu {

    @JSONField(name = "type")
    protected String mType;

    public SubButton(String name, String type) {
        super(name);
        setType(type);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
