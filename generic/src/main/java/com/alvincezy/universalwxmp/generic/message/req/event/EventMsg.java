package com.alvincezy.universalwxmp.generic.message.req.event;

import com.alvincezy.universalwxmp.generic.message.WxMsg;
import com.alvincezy.universalwxmp.util.xml.XmlNode;
import com.alvincezy.universalwxmp.util.xml.annotation.Element;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract event msg <a href="http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html">see here</a>
 * <p/>
 * Created by Administrator on 2016/1/18.
 *
 * @author alvince.zy@gmail.com
 */
public abstract class EventMsg extends WxMsg {

    public static final String EVENT_CLICK = "CLICK";
    public static final String EVENT_LOCATION = "LOCATION";
    public static final String EVENT_SCAN = "SCAN";
    public static final String EVENT_SUBSCRIBE = "subscribe";
    public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_VIEW = "VIEW";

    public static final String DOC_ELE_EVENT = "Event";

    enum Event {
        ACTION, LOCATION, SUBSCRIBE, TICKET
    }

    @Element(name = DOC_ELE_EVENT, raw = false)
    protected String mEvent;

    public EventMsg(String fromUser, String toUser, long createTime, String event) {
        super(fromUser, toUser, MSG_TYPE_EVENT, createTime);
        setEvent(event);
    }

    @Override
    public String toString() {
        return super.toString() + ", Event='" + mEvent + '\'';
    }

    public String getEvent() {
        return mEvent;
    }

    public void setEvent(String event) {
        mEvent = event;
    }

    /**
     * Event msg builder.
     */
    public static class Builder extends WxMsg.Builder {

        private Event mEvent;
        private String mEventStr;
        private Map<String, XmlNode> mAdditional;

        public Builder attr(List<XmlNode> attrs) {
            for (XmlNode node : attrs) {
                String name = node.getName();
                check:
                if (StringUtils.equals(DOC_ELE_EVENT, name)) {
                    String event = node.getValue();
                    if (StringUtils.equals(EVENT_LOCATION, event)) {
                        mEvent = Event.LOCATION;
                        break check;
                    } else if (StringUtils.equals(EVENT_SCAN, event)) {
                        mEvent = Event.TICKET;
                    } else if (StringUtils.equals(EVENT_SUBSCRIBE, event) || StringUtils.equals(EVENT_UNSUBSCRIBE, event)) {
                        mEvent = Event.SUBSCRIBE;
                    } else {
                        mEvent = Event.ACTION;
                    }
                    mEventStr = event;
                } else if (StringUtils.equals(DOC_ELE_FROM_USER_NAME, name)
                        || StringUtils.equals(DOC_ELE_TO_USER_NAME, name)
                        || StringUtils.equals(DOC_ELE_CREATE_TIME, name)) {
                    set(name, node);
                } else {
                    getAdditional().put(name, node);
                }
            }

            return this;
        }

        public EventMsg build() {
            EventMsg msg = null;
            //System.out.println(mAdditional);
            branch:switch (mEvent) {
                case LOCATION:
                    msg = new LocationEvent(from, to, createTime,
                            mAdditional.get(LocationEvent.DOC_ELE_LATITUDE).getDouble(),
                            mAdditional.get(LocationEvent.DOC_ELE_LONGITUDE).getDouble(),
                            mAdditional.get(LocationEvent.DOC_ELE_PRECISION).getDouble());
                    break;
                case SUBSCRIBE:
                    if (mAdditional == null) {
                        msg = new SubscribeEvent(from, to, createTime, mEventStr);
                        break branch;
                    }
                case TICKET:
                    msg = new TicketEvent(from, to, createTime, mEventStr, mAdditional.get(ActionEvent.DOC_ELE_EVENT_KEY).getValue(),
                            mAdditional.get(TicketEvent.DOC_ELE_TICKET).getValue());
                    break;
                case ACTION:
                    msg = new ActionEvent(from, to, createTime, mEventStr, mAdditional.get(ActionEvent.DOC_ELE_EVENT_KEY).getValue());
                default:
            }

            if (mAdditional != null) {
                mAdditional.clear();
            }

            return msg;
        }

        private Map<String, XmlNode> getAdditional() {
            if (mAdditional == null) {
                mAdditional = new HashMap<String, XmlNode>();
            }
            return mAdditional;
        }
    }
}
