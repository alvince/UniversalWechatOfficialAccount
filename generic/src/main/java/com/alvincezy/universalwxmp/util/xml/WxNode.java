package com.alvincezy.universalwxmp.util.xml;

import com.alvincezy.universalwxmp.generic.message.WxMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WxNode extends XmlNode {

    private XmlNode mTypeNode;

    public WxNode(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "WxNode<" + mName + ", " + mValue + ">";
    }

    @Override
    public void addNode(XmlNode node) {
        if (StringUtils.equals(WxMsg.DOC_ELE_MSG_TYPE, node.getName())) {
            mTypeNode = node;
        }
        super.addNode(node);
    }

    public XmlNode getType() {
        return mTypeNode;
    }
}
