package com.alvincezy.universalwxmp.util.xml;

import com.alvincezy.universalwxmp.util.common.ClassUtils;
import com.alvincezy.universalwxmp.util.common.StringUtilsExtra;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import com.alvincezy.universalwxmp.util.xml.parser.ElementInfo;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class XmlBuilder {

    private static final String NODE_LIST_ITEM_TAG = "item";
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

    private static XmlNode parseToXml(Class<?> clazz, Object obj, XmlNode parent) throws IllegalAccessException {
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

        String name = getTagName(annoInfo, field.getName());
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
        String annotatedName = getTagName(annoInfo, StringUtils.EMPTY);
        XmlNode node = new XmlNode(annotatedName);
        Class<?> fieldCls = field.getType();

        if (ClassUtils.isCollectionType(fieldCls)) {
            Collection list = (Collection) ClassUtils.getFieldValue(field, obj);
            if (!list.isEmpty()) {
                Type listItemType = field.getGenericType();
                if (listItemType instanceof ParameterizedType) {
                    Class listParamCls = (Class) ((ParameterizedType) listItemType).getActualTypeArguments()[0];
                    ElementInfo listParamClsEle = ElementInfo.build((Element) listParamCls.getAnnotation(Element.class));
                    String listParamTagName = listParamClsEle == null || listParamClsEle.isEmbed()
                            ? NODE_LIST_ITEM_TAG : listParamCls.getSimpleName();
                    for (Object listItem : list) {
                        XmlNode subNode = parseToXml(listItem.getClass(), listItem, null);
                        subNode.setName(listParamTagName);
                        node.addNode(subNode);
                    }
                }
            }
        } else {
            if (StringUtils.isEmpty(annotatedName)) {
                node.setName(fieldCls.getSimpleName());
            }
            parseToXml(fieldCls, ClassUtils.getFieldValue(field, obj), node);
        }

        return node;
    }

    private static String getTagName(ElementInfo elementAnnotation, String defName) {
        if (elementAnnotation != null) {
            String markedName = elementAnnotation.getName();
            if (!StringUtils.isEmpty(markedName)) {
                return markedName;
            }
        }
        return defName;
    }
}
