package com.alvincezy.universalwxmp.generic.message.req.event;

/**
 * Subscribe event msg.
 * <br/>
 * Provide basic event info <a href=
 * "http://mp.weixin.qq.com/wiki/7/9f89d962eba4c5924ed95b513ba69d9b.html#.E5.85.B3.E6.B3.A8.2F.E5.8F.96.E6.B6.88.E5.85.B3.E6.B3.A8.E4.BA.8B.E4.BB.B6">see detail</a>
 * <p/>
 * Created by Administrator on 2016/1/18.
 *
 * @author alvince.zy@gmail.com
 */
public class SubscribeEvent extends EventMsg {

    public SubscribeEvent(String fromUser, String toUser, long createTime, String event) {
        super(fromUser, toUser, createTime, event);
    }

    @Override
    public String toString() {
        return "SubscribeEvent{" + super.toString() + "}";
    }
}
