package com.alvincezy.universalwxmp.generic.message.xml;

import com.alvincezy.universalwxmp.generic.message.WXMsg;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * WeChat-official message xml parse handler
 * <p>
 * Created by Administrator on 2016/2/24.
 *
 * @author alvince.zy@gmail.com
 */
public class WXXmlParseHandler extends DefaultHandler {

    private int eleLevel;
    private WXNode root;
    private Map<Integer, WXNode> nodeIndicator;

    @Override
    public void startDocument() throws SAXException {
        eleLevel = 0;
        nodeIndicator = new HashMap<Integer, WXNode>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (eleLevel++ == 0 && qName.equals(WXMsg.DOC_ELE_ROOT)) {
            root = new WXNode(qName);
            nodeIndicator.put(eleLevel, root);
        } else {
            WXNode node = new WXNode(qName);
            WXNode parent = nodeIndicator.get(eleLevel - 1);
            parent.addNode(node);
            nodeIndicator.put(eleLevel, node);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        WXNode currNode = nodeIndicator.get(eleLevel);
        if (currNode != null) {
            currNode.setValue(new String(ch, start, length));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        nodeIndicator.remove(eleLevel--);
    }

    @Override
    public void endDocument() throws SAXException {
        nodeIndicator.clear();
        nodeIndicator = null;
    }

    public WXNode result() {
        WXNode node = root;
        root = null;
        return node;
    }
}
