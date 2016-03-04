package com.alvincezy.universalwxmp.generic.message.xml;

import com.alvincezy.universalwxmp.generic.message.WXMsg;
import com.alvincezy.universalwxmp.util.xml.XmlNode;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WXNode extends XmlNode {

    private XmlNode mTypeNode;

    public WXNode(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "WXNode<" + mName + ", " + mValue + ">";
    }

    @Override
    public void addNode(XmlNode node) {
        if (StringUtils.equals(WXMsg.DOC_ELE_MSG_TYPE, node.getName())) {
            mTypeNode = node;
        } else {
            super.addNode(node);
        }
    }

    public XmlNode getType() {
        return mTypeNode;
    }
}
