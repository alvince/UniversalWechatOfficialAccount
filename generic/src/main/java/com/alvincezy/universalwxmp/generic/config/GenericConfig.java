package com.alvincezy.universalwxmp.generic.config;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * Created by Administrator on 2016/1/18.
 *
 * @author alvince.zy@gmail.com
 */
public class GenericConfig {

    public static final String TAG = "WechatGenericConfig";

    public static final String CONF_WECHAT_OFFICIAL_COMMUNICATE_ENCRYPT = "wechat.official.communicate.encrypt";
    public static final String CONF_WECHAT_OFFICIAL_COMMUNICATE_KEY = "wechat.official.communicate.key";
    public static final String CONF_WECHAT_OFFICIAL_TOKEN = "wechat.official.token";

    private static GenericConfig mInstance;

    public static GenericConfig getInstance() {
        if (mInstance == null) {
            mInstance = new GenericConfig();
        }
        return mInstance;
    }

    private boolean mEncrypt;

    private byte[] mEncryptKey;

    private String mWeChatToken;

    private GenericConfig() {
        mEncrypt = false;
        mEncryptKey = null;
        mWeChatToken = StringUtils.EMPTY;
    }

    public void set(Properties props) {
        if (props != null) {
            setWeChatToken(props.getProperty(CONF_WECHAT_OFFICIAL_TOKEN));
            setEncrypt(props.getProperty(CONF_WECHAT_OFFICIAL_COMMUNICATE_ENCRYPT, "false"));
            String encodingAesKey = props.getProperty(CONF_WECHAT_OFFICIAL_COMMUNICATE_KEY, StringUtils.EMPTY);
            if (!StringUtils.isEmpty(encodingAesKey)) {
                setEncryptKey(Base64.decodeBase64(encodingAesKey + "="));
            }
        }
    }

    public boolean isEncrypt() {
        return mEncrypt;
    }

    public byte[] getEncryptKey() {
        return mEncryptKey;
    }

    public String getWeChatToken() {
        return mWeChatToken;
    }

    public void setEncrypt(String encrypt) {
        mEncrypt = Boolean.parseBoolean(encrypt);
    }

    public void setEncrypt(boolean encrypt) {
        mEncrypt = encrypt;
    }

    public void setEncryptKey(byte[] encryptKey) {
        mEncryptKey = encryptKey;
    }

    public void setWeChatToken(String weChatToken) {
        mWeChatToken = weChatToken;
    }
}
