package com.alvincezy.universalwxmp.util.xml;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class XmlTransformer {

    private static final String ENCODING = "UTF-8";

    private static SAXTransformerFactory mSAXTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

    public static String transform(Object obj) throws
            IllegalAccessException, TransformerConfigurationException, SAXException {
        // build object' xml nodes
        XmlNode root = XmlBuilder.parseToXml(obj);

        StringWriter writer = new StringWriter();
        Result resultXml = new StreamResult(writer);
        TransformerHandler handler = mSAXTransformerFactory.newTransformerHandler();
        handler.setResult(resultXml);
        Transformer transformer = handler.getTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, ENCODING);

        handler.startDocument();
        transformXmlNode(handler, root);
        handler.endDocument();

        String resultStr = writer.getBuffer().toString();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public static void createXmlElement(TransformerHandler handler, XmlNode node) throws SAXException {
        String uri = node.getUri();
        String lName = node.getLocalName();
        String qName = node.getName();

        // start element
        handler.startElement(uri, lName, qName, node.getAttributesEntity());

        String value = node.getValue();
        if (!StringUtils.isEmpty(value)) {
            if (node.isCData()) {
                handler.startCDATA();
                handler.characters(value.toCharArray(), 0, value.length());
                handler.endCDATA();
            } else {
                handler.characters(value.toCharArray(), 0, value.length());
            }
        }

        // end element automatic
        if (!node.hasSubset()) {
            handler.endElement(uri, lName, qName);
        }
    }

    private static void transformXmlNode(TransformerHandler handler, XmlNode node) throws SAXException {
        boolean isParent = node.hasSubset();
        createXmlElement(handler, node);
        if (node.hasSubset()) {
            for (XmlNode child : node.list()) {
                transformXmlNode(handler, child);
            }
        }

        if (isParent) {
            handler.endElement(node.getUri(), node.getLocalName(), node.getName());
        }
    }
}
