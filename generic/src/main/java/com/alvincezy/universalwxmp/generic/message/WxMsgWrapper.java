package com.alvincezy.universalwxmp.generic.message;

import com.alvincezy.universalwxmp.generic.message.req.*;
import com.alvincezy.universalwxmp.generic.message.resp.RespMsg;
import com.alvincezy.universalwxmp.util.common.ClassUtils;
import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.XmlBuilder;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WxMsgWrapper {

    private static final String ENCODING = "UTF-8";
    private static SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
    private static SAXTransformerFactory mSAXTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

    /**
     * Type of WeChat-mp message received.
     */
    public enum Type {
        IMAGE, LINK, LOCATION, TEXT, SHORT_VIDEO, VIDEO, VOICE
    }

    public WxMsg msg;
    public Type type;

    /**
     * Parse wechat-mp message xml from stream.
     *
     * @param xmlStream xml input stream
     * @return msg wrapper
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static WxMsgWrapper parseXml(InputStream xmlStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = mSAXParserFactory.newSAXParser();
        WxXmlParseHandler handler = new WxXmlParseHandler();
        parser.parse(xmlStream, handler);

        WxNode root = handler.result();
        Map<String, WxNode> nodes = root.list();
        System.out.println(nodes);
        WxMsgWrapper wrapper = new WxMsgWrapper();

        String msgType = root.list().get(WxMsg.DOC_ELE_MSG_TYPE).getValue();
        if (StringUtils.equals(RecMsg.MSG_TYPE_IMAGE, msgType)) {
            wrapper.type = Type.IMAGE;
            wrapper.msg = new ImageMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_LINK, msgType)) {
            wrapper.type = Type.LINK;
            wrapper.msg = new LinkMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_LOCATION, msgType)) {
            wrapper.type = Type.LOCATION;
            wrapper.msg = new LocationMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_TEXT, msgType)) {
            wrapper.type = Type.TEXT;
            wrapper.msg = new TextMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_VIDEO, msgType)) {
            wrapper.type = Type.VIDEO;
            wrapper.msg = new VideoMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_VIDEO_SHORT, msgType)) {
            wrapper.type = Type.SHORT_VIDEO;
            wrapper.msg = new ShortVideoMsg.Builder().attr(nodes).build();
        } else if (StringUtils.equals(RecMsg.MSG_TYPE_VOICE, msgType)) {
            wrapper.type = Type.VOICE;
            wrapper.msg = new VoiceMsg.Builder().attr(nodes).build();
        }

        return wrapper;
    }

    /**
     * Transform message to xml-text.
     *
     * @param message xml-text
     */
    public static String transform(RespMsg message, boolean format) throws
            TransformerConfigurationException, SAXException, IllegalAccessException, IOException, NoSuchFieldException {
        StringWriter writer = new StringWriter();
        Result resultXml = new StreamResult(writer);
        TransformerHandler handler = mSAXTransformerFactory.newTransformerHandler();
        handler.setResult(resultXml);
        Transformer transformer = handler.getTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
        if (format) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }

        Attributes attr = new AttributesImpl();
        handler.startDocument();
        XmlBuilder.startXmlElement(handler, WxMsg.DOC_ELE_ROOT, null, attr, false);

        Class clazz = message.getClass();
        System.out.println(">>>Class>>>" + clazz.getName());
        for (Field field : ClassUtils.getAllMemberFields(clazz)) {
            System.out.println(">>>Field>>>" + field.getName());
            boolean inaccessible = !field.isAccessible();
            if (inaccessible) {
                field.setAccessible(true);
            }

            Element element = field.getDeclaredAnnotation(Element.class);
            if (element != null && !element.ignore()) {
                String eName = element.name();
                if (StringUtils.isEmpty(eName)) {
                    Element fieldClsElementAnno = field.getType().getAnnotation(Element.class);
                    if (fieldClsElementAnno != null) {
                        eName = fieldClsElementAnno.name();
                    }
                }

                if (element.embed()) {
                    if (StringUtils.isEmpty(eName)) {
                        eName = field.getType().getSimpleName();
                    }

                    Object value = field.get(message);
                } else {
                    if (StringUtils.isEmpty(eName)) {
                        eName = field.getName();
                    }

                    if (field.getType().isAssignableFrom(Collection.class)) {
                        addListXmlElement(handler, eName, attr, (Collection) field.get(message));
                    } else {
                        XmlBuilder.startXmlElement(handler, eName, field.get(message), attr, !element.raw(), true);
                    }
                }
            }

            if (inaccessible) {
                field.setAccessible(false);
            }
        }

        handler.endElement(StringUtils.EMPTY, StringUtils.EMPTY, WxMsg.DOC_ELE_ROOT);
        handler.endDocument();
        String resultStr = writer.getBuffer().toString();
        writer.close();
        return resultStr;
    }

    private static void addListXmlElement(TransformerHandler handler, String name, Attributes attributes, Collection list) throws SAXException {
        for (Object item : list) {
            XmlBuilder.startXmlElement(handler, RespMsg.DOC_ELE_LIST_ITEM, item, attributes, false);

            handler.endElement(StringUtils.EMPTY, StringUtils.EMPTY, RespMsg.DOC_ELE_LIST_ITEM);
        }
    }

    /**
     * WeChat-mp message xml parse handler.
     */
    private static class WxXmlParseHandler extends DefaultHandler {

        private int eleLevel;
        private WxNode root;
        private Map<Integer, WxNode> nodeIndicator;

        @Override
        public void startDocument() throws SAXException {
            eleLevel = 0;
            nodeIndicator = new HashMap<Integer, WxNode>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (eleLevel++ == 0 && qName.equals(WxMsg.DOC_ELE_ROOT)) {
                root = new WxNode(qName);
                nodeIndicator.put(eleLevel, root);
            } else {
                WxNode node = new WxNode(qName);
                WxNode parent = nodeIndicator.get(eleLevel - 1);
                parent.addNode(node);
                nodeIndicator.put(eleLevel, node);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            WxNode currNode = nodeIndicator.get(eleLevel);
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

        public WxNode result() {
            WxNode node = root;
            root = null;
            return node;
        }
    }
}
