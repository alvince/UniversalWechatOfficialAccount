package com.alvincezy.universalwxmp.generic.message.req.event;

import com.alvincezy.universalwxmp.util.xml.annotation.Element;

/**
 * Qrcode scan event msg.
 * <br/>
 * Provide event info with qrcode-params <a href=
 * "http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html#.E6.89.AB.E6.8F.8F.E5.B8.A6.E5.8F.82.E6.95.B0.E4.BA.8C.E7.BB.B4.E7.A0.81.E4.BA.8B.E4.BB.B6">see detail</a>
 * <p/>
 * Created by Administrator on 2016/1/18.
 *
 * @author alvince.zy@gmail.com
 */
public class TicketEvent extends ActionEvent {

    static final String DOC_ELE_TICKET = "Ticket";

    @Element(name = DOC_ELE_TICKET)
    private String mTicket;

    public TicketEvent(String fromUser, String toUser, long createTime, String event, String eventKey, String ticket) {
        super(fromUser, toUser, createTime, event, eventKey);
        setTicket(ticket);
    }

    @Override
    public String toString() {
        return "TicketEvent{" + show() + ", Ticket='" + mTicket + "'}";
    }

    public String getTicket() {
        return mTicket;
    }

    public void setTicket(String ticket) {
        mTicket = ticket;
    }
}
