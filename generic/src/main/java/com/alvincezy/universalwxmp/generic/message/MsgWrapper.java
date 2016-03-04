package com.alvincezy.universalwxmp.generic.message;

import com.alvincezy.universalwxmp.generic.message.req.*;
import com.alvincezy.universalwxmp.generic.message.req.event.EventMsg;
import com.alvincezy.universalwxmp.generic.message.xml.WXNode;
import com.alvincezy.universalwxmp.util.xml.TransformException;
import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.XmlTransformer;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class MsgWrapper {

    private static SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();

    /**
     * Type of WeChat-mp message received.
     */
    public enum Type {
        EVENT, IMAGE, LINK, LOCATION, TEXT, SHORT_VIDEO, VIDEO, VOICE
    }

    public WXMsg msg;
    public Type type;

    public MsgWrapper() {
    }

    public MsgWrapper(WXMsg msg) {
        this.msg = msg;
    }

    /**
     * Transform message to xml-text.
     * <br/>
     * Will throw {@link IllegalStateException} when the msg is not set.
     *
     * @return xml text
     * @throws TransformException when transform exception happened.
     */
    public String transform2Xml() throws TransformException {
        if (msg == null) {
            throw new IllegalStateException("No message to transform: no msg set.");
        }

        try {
            return XmlTransformer.transform(msg);
        } catch (IllegalAccessException e) {
            throw new TransformException(e.getMessage(), e);
        } catch (TransformerConfigurationException e) {
            throw new TransformException(e.getMessage(), e);
        } catch (SAXException e) {
            throw new TransformException(e.getMessage(), e);
        }
    }

    /**
     * Parse wechat-mp message xml from stream.
     *
     * @param xmlStream xml input stream
     * @return msg wrapper
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static MsgWrapper parseXml(InputStream xmlStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = mSAXParserFactory.newSAXParser();
        WxXmlParseHandler handler = new WxXmlParseHandler();
        parser.parse(xmlStream, handler);

        WXNode root = handler.result();
        List<XmlNode> nodes = root.list();
        MsgWrapper wrapper = new MsgWrapper();

        String msgType = root.getType().getValue();
        if (StringUtils.equals(WXMsg.MSG_TYPE_IMAGE, msgType)) {
            wrapper.type = Type.IMAGE;
            wrapper.msg = new ImageMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_LINK, msgType)) {
            wrapper.type = Type.LINK;
            wrapper.msg = new LinkMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_LOCATION, msgType)) {
            wrapper.type = Type.LOCATION;
            wrapper.msg = new LocationMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_TEXT, msgType)) {
            wrapper.type = Type.TEXT;
            wrapper.msg = new TextMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_VIDEO, msgType)) {
            wrapper.type = Type.VIDEO;
            wrapper.msg = new VideoMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_VIDEO_SHORT, msgType)) {
            wrapper.type = Type.SHORT_VIDEO;
            wrapper.msg = new ShortVideoMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_VOICE, msgType)) {
            wrapper.type = Type.VOICE;
            wrapper.msg = new VoiceMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(WXMsg.MSG_TYPE_EVENT, msgType)) {
            wrapper.type = Type.EVENT;
            wrapper.msg = new EventMsg.Builder().attr(nodes).build();
        }

        return wrapper;
    }

    /**
     * WeChat-mp message xml parse handler.
     */
    private static class WxXmlParseHandler extends DefaultHandler {

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
}
