package com.alvincezy.universalwxmp.util.xml;

import com.alvincezy.universalwxmp.util.common.ClassUtils;
import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import com.alvincezy.universalwxmp.util.xml.parser.ElementInfo;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.transform.sax.TransformerHandler;
import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class XmlBuilder {

    private static final String NODE_VALUE_NUMBER_DEF = "0";

    public static XmlNode parseToXml(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        ElementInfo annoInfo = ElementInfo.build(clazz.getAnnotation(Element.class));
        boolean isNameMarked = annoInfo != null && !StringUtils.isEmpty(annoInfo.getName());
        String markName = isNameMarked ? annoInfo.getName() : clazz.getSimpleName();
        XmlNode root = new XmlNode(StringUtils.isEmpty(markName) ? clazz.getSimpleName() : markName);
        parseToXml(clazz, obj, root);
        return root;
    }

    public static void startXmlElement(TransformerHandler handler, String name, Object value, Attributes attributes, boolean useCData)
            throws SAXException {
        startXmlElement(handler, name, value, attributes, useCData, false);
    }

    public static void startXmlElement(TransformerHandler handler, String name, Object value, Attributes attributes, boolean useCData, boolean autoEnd)
            throws SAXException {
        // start element
        handler.startElement(StringUtils.EMPTY, StringUtils.EMPTY, name, attributes);

        if (value != null) {
            String text = String.valueOf(value);

            if (!StringUtils.isEmpty(text)) {
                if (useCData) {
                    handler.startCDATA();
                    handler.characters(text.toCharArray(), 0, text.length());
                    handler.endCDATA();
                } else {
                    handler.characters(text.toCharArray(), 0, text.length());
                }
            }
        }

        // auto end element
        if (autoEnd) {
            handler.endElement(StringUtils.EMPTY, StringUtils.EMPTY, name);
        }
    }

    private static XmlNode parseToXml(Class<?> clazz, Object obj, XmlNode parent) throws IllegalAccessException {
        System.out.println("Parsing class>>>" + clazz.getName());
        XmlNode node = parent == null ? new XmlNode() : parent;
        for (Field field : ClassUtils.getAllMemberFields(clazz)) {
            ElementInfo annoInfo = ElementInfo.build(field.getAnnotation(Element.class));
            if (annoInfo != null && annoInfo.isIgnore()) {
                continue;
            }

            Class<?> fieldCls = field.getType();
            if (ClassUtils.isBasicType(fieldCls)) {
                node.addNode(tranformBasicType(field, annoInfo, obj));
            } else {
                node.addNode(transformRefType(field, annoInfo, obj));
            }
        }
        return node;
    }

    private static XmlNode tranformBasicType(Field field, ElementInfo annoInfo, Object obj) throws IllegalAccessException {
        boolean isNumberType = ClassUtils.isNumberType(field.getType());

        String name = getMarkName(annoInfo, field.getName());
        XmlNode node = new XmlNode(name);

        if (annoInfo != null) {
            node.setCData(!annoInfo.isRaw());
        } else {
            node.setCData(!isNumberType);
        }

        String var = StringUtilsExtra.varWithoutNull(
                String.valueOf(ClassUtils.getFieldValue(field, obj)));
        node.setValue(isNumberType && StringUtils.isEmpty(var) ? NODE_VALUE_NUMBER_DEF : var);

        return node;
    }

    private static XmlNode transformRefType(Field field, ElementInfo annoInfo, Object obj) throws IllegalAccessException {
        Class<?> fieldCls = field.getType();
        String name = getMarkName(annoInfo, StringUtils.EMPTY);
        if (StringUtils.isEmpty(name)) {
            name = getMarkName(ElementInfo.build(
                    fieldCls.getAnnotation(Element.class)), field.getName());
        }
        XmlNode node = new XmlNode(name);


        if (ClassUtils.isCollectionType(fieldCls)) {
            // TODO handle collection field
            parseToXml(fieldCls, name, null);
        } else {
            parseToXml(fieldCls, ClassUtils.getFieldValue(field, obj), node);
        }

        return node;
    }

    private static String getMarkName(ElementInfo elementAnnotation, String defName) {
        if (elementAnnotation != null) {
            String markedName = elementAnnotation.getName();
            if (!StringUtils.isEmpty(markedName)) {
                return markedName;
            }
        }
        return defName;
    }
}
