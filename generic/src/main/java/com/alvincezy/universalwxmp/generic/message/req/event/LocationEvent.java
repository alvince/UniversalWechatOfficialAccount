package com.alvincezy.universalwxmp.generic.message.req.event;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Location event msg.
 * <br/>
 * Provide location information <a href=
 * "http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6">see detail</a>
 * <p/>
 * Created by Administrator on 2016/1/18.
 *
 * @author alvince.zy@gmail.com
 */
public class LocationEvent extends EventMsg {

    static final String DOC_ELE_LATITUDE = "Latitude";
    static final String DOC_ELE_LONGITUDE = "Longitude";
    static final String DOC_ELE_PRECISION = "Precision";

    @Element(name = DOC_ELE_LATITUDE)
    private double mLat;

    @Element(name = DOC_ELE_LONGITUDE)
    private double mLng;

    @Element(name = DOC_ELE_PRECISION)
    private double mPrecision;

    public LocationEvent(String fromUser, String toUser, long createTime, double latitude, double longitude, double precision) {
        super(fromUser, toUser, createTime, EVENT_LOCATION);
        setLatitude(latitude);
        setLongitude(longitude);
        setPrecision(precision);
    }

    @Override
    public String toString() {
        return "LocationEvent{" + super.toString() + ", Lat=" + mLat + ", Lng=" + mLng + ", Precision=" + mPrecision + '}';
    }

    public double getLatitude() {
        return mLat;
    }

    public double getLongitude() {
        return mLng;
    }

    public double getPrecision() {
        return mPrecision;
    }

    public void setLatitude(double latitude) {
        mLat = latitude;
    }

    public void setLongitude(double longitude) {
        mLng = longitude;
    }

    public void setPrecision(double precision) {
        mPrecision = precision;
    }
}
