package com.alvincezy.universalwxmp.util.xml.parser;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class ElementInfo {

    private String mName;

    private boolean mEmbed;

    private boolean mRaw;

    private boolean mIgnore;

    public ElementInfo(String name, boolean embed, boolean raw, boolean ignore) {
        setName(name);
        setEmbed(embed);
        setRaw(raw);
        setIgnore(ignore);
    }

    public String getName() {
        return mName;
    }

    public boolean isEmbed() {
        return mEmbed;
    }

    public boolean isRaw() {
        return mRaw;
    }

    public boolean isIgnore() {
        return mIgnore;
    }

    protected void setName(String name) {
        mName = name;
    }

    protected void setEmbed(boolean embed) {
        mEmbed = embed;
    }

    protected void setRaw(boolean raw) {
        mRaw = raw;
    }

    protected void setIgnore(boolean ignore) {
        mIgnore = ignore;
    }

    public static ElementInfo build(Element element) {
        return element == null ? null
                : new ElementInfo(element.name(), element.embed(), element.raw(), element.ignore());
    }
}
