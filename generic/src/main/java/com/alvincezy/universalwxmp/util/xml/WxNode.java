package com.alvincezy.universalwxmp.util.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class WxNode {

    private String name;

    private String value;

    private Map<String, WxNode> nodeList;

    public WxNode(String name) {
        setName(name);
    }

    @Override
    public String toString() {
        return "WxNode<" + name + ", " + value + ">";
    }

    public void addNode(WxNode node) {
        if (node != null) {
            list(true).put(node.getName(), node);
        }
    }

    public double getDouble() {
        return Double.parseDouble(value);
    }

    public int getInt() {
        return Integer.parseInt(value);
    }

    public long getLong() {
        return Long.parseLong(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Map<String, WxNode> list() {
        return list(false);
    }

    public Map<String, WxNode> list(boolean makeNew) {
        if (nodeList == null && makeNew) {
            nodeList = new HashMap<String, WxNode>();
        }
        return nodeList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
