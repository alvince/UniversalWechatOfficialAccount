package com.alvincezy.universalwxmp.generic.message.req.event;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Action menu event msg.
 * <br/>
 * Provide custom wechat menu action information <a href=
 * "http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html#.E8.87.AA.E5.AE.9A.E4.B9.89.E8.8F.9C.E5.8D.95.E4.BA.8B.E4.BB.B6">see detail</a>
 * <p/>
 * Created by Administrator on 2016/1/18.
 *
 * @author alvince.zy@gmail.com
 */
public class ActionEvent extends EventMsg {

    static final String DOC_ELE_EVENT_KEY = "EventKey";

    @Element(name = DOC_ELE_EVENT_KEY)
    protected String mEventKey;

    public ActionEvent(String fromUser, String toUser, long createTime, String event, String eventKey) {
        super(fromUser, toUser, createTime, event);
        setEventKey(eventKey);
    }

    @Override
    public String toString() {
        return "ActionEvent{" + show() + '}';
    }

    public String getEventKey() {
        return mEventKey;
    }

    public void setEventKey(String eventKey) {
        mEventKey = eventKey;
    }

    protected String show() {
        return super.toString() + ", EventKey='" + mEventKey + '\'';
    }
}
