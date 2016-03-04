package com.alvincezy.universalwxmp.generic;

/**
 * Created by Administrator on 2016/2/29.
 *
 * @author alvince.zy@gmail.com
 */
public class WXEncryptReqBundle {

    public static final String PARAM_MSG_SIGNATURE = "msg_signature";
    public static final String PARAM_NONCE = "nonce";
    public static final String PARAM_TIMESTAMP = "timestamp";

    private String nonce;

    private String postData;

    private String msgSignature;

    private String timestamp;

    public WXEncryptReqBundle(String nonce, String msgSignature, String timestamp) {
        this(nonce, msgSignature, timestamp, "");
    }

    public WXEncryptReqBundle(String nonce, String msgSignature, String timestamp, String postData) {
        setNonce(nonce);
        setMsgSignature(msgSignature);
        setTimestamp(timestamp);
        setPostData(postData);
    }

    public String getNonce() {
        return nonce;
    }

    public String getPostData() {
        return postData;
    }

    public String getMsgSignature() {
        return msgSignature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public void setMsgSignature(String signature) {
        this.msgSignature = signature;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
