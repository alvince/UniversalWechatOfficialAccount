package com.alvincezy.universalwxmp.generic.message.req;

import com.alvincezy.universalwxmp.generic.message.WxMsg;
import com.alvincezy.universalwxmp.util.xml.WxNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;

import java.util.Map;

/**
 * Location message received.
 * <p/>
 * Created by Administrator on 2016/1/12.
 *
 * @author alvince.zy@gmail.com
 */
public class LocationMsg extends RecMsg {

    @Element(name = DOC_ELE_LOCATION_X)
    private double mLocX;

    @Element(name = DOC_ELE_LOCATION_Y)
    private double mLocY;

    @Element(name = DOC_ELE_SCALE)
    private int mScale;

    @Element(name = DOC_ELE_LABEL)
    private String mLabel;

    public LocationMsg(long msgId, String fromUser, String toUser, long createTime, double locationX, double locationY, int scale, String label) {
        super(msgId, fromUser, toUser, MSG_TYPE_LOCATION, createTime);
        setLocationX(locationX);
        setLocationY(locationY);
        setScale(scale);
        setLabel(label);
    }

    @Override
    public String toString() {
        return "LocationMsg{" + super.toString() + ", LocX=" + mLocX + ", LocY=" + mLocY + ", Scale=" + mScale + ", Label='" + mLabel + "'}";
    }

    public double getLocationX() {
        return mLocX;
    }

    public double getLocationY() {
        return mLocY;
    }

    public int getScale() {
        return mScale;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLocationX(double locationX) {
        mLocX = locationX;
    }

    public void setLocationY(double locationY) {
        mLocY = locationY;
    }

    public void setScale(int scale) {
        mScale = scale;
    }

    public void setLabel(String label) {
        mLabel = label;
    }


    public static class Builder extends RecMsg.Builder {
        private double locationX;
        private double locationY;
        private int scale;
        private String label;

        @Override
        public Builder attr(Map<String, WxNode> attrs) {
            if (attrs != null && !attrs.isEmpty()) {
                set(attrs);
                locationX = attrs.get(WxMsg.DOC_ELE_LOCATION_X).getDouble();
                locationY = attrs.get(WxMsg.DOC_ELE_LOCATION_Y).getDouble();
                scale = attrs.get(WxMsg.DOC_ELE_SCALE).getInt();
                label = attrs.get(WxMsg.DOC_ELE_LABEL).getValue();
            }
            return this;
        }

        @Override
        public LocationMsg build() {
            return new LocationMsg(msgId, from, to, createTime, locationX, locationY, scale, label);
        }
    }
}
