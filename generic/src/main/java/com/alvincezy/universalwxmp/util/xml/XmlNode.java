package com.alvincezy.universalwxmp.util.xml;

import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class XmlNode {

    protected String mUri;

    protected String lName;

    protected String mName;

    protected String mValue;

    protected Attributes mAttributes;

    protected List<XmlNode> mSublist;

    protected boolean mUseCData;

    public XmlNode() {
        this(null, null);
    }

    public XmlNode(String name) {
        this(name, null);
    }

    public XmlNode(String name, String value) {
        this(null, null, name, value);
    }

    public XmlNode(String uri, String localName, String name, String value) {
        setUri(uri);
        setLocalName(localName);
        setName(name);
        setValue(value);
    }

    @Override
    public String toString() {
        return "XmlNode<" + mName + ", " + mValue + ">";
    }

    public void addNode(XmlNode node) {
        if (node != null) {
            if (mSublist == null) {
                mSublist = new ArrayList<XmlNode>();
            }
            mSublist.add(node);
        }
    }

    public double getDouble() {
        return Double.parseDouble(mValue);
    }

    public int getInt() {
        return Integer.parseInt(mValue);
    }

    public long getLong() {
        return Long.parseLong(mValue);
    }

    public boolean hasSubset() {
        return mSublist != null && !mSublist.isEmpty();
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(mName) && StringUtils.isEmpty(mValue) && StringUtils.isEmpty(lName) && StringUtils.isEmpty(mUri) && (mAttributes == null);
    }

    public List<XmlNode> list() {
        return mSublist == null ? Collections.<XmlNode>emptyList() : mSublist;
    }

    public Attributes getAttributes() {
        return mAttributes;
    }

    public Attributes getAttributesEntity() {
        if (mAttributes == null) {
            mAttributes = new AttributesImpl();
        }
        return mAttributes;
    }

    public String getName() {
        return StringUtilsExtra.var(mName);
    }

    public String getValue() {
        return StringUtilsExtra.var(mValue);
    }

    public boolean isCData() {
        return mUseCData;
    }

    public String getUri() {
        return StringUtilsExtra.var(mUri);
    }

    public String getLocalName() {
        return StringUtilsExtra.var(lName);
    }

    public void setAttributes(Attributes attributes) {
        if (attributes != null && attributes.getLength() > 0) {
            this.mAttributes = attributes;
        }
    }

    public void setName(String name) {
        if (!StringUtils.isEmpty(name)) {
            mName = name;
        }
    }

    public void setValue(String value) {
        mValue = value;
    }

    public void setCData(boolean cData) {
        mUseCData = cData;
    }

    public void setUri(String uri) {
        if (!StringUtils.isEmpty(uri)) {
            mUri = uri;
        }
    }

    public void setLocalName(String localName) {
        if (!StringUtils.isEmpty(localName)) {
            lName = localName;
        }
    }
}
