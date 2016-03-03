package com.alvincezy.universalwxmp.generic.config;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * Wechat official acount message encrypt config
 * <p>
 * Created by Administrator on 2016/2/23.
 *
 * @author alvince.zy@gmail.com
 */
public class EncryptConfig {

    public static final String TAG = EncryptConfig.class.getName();

    public static final String WECHAT_OFFICIAL_APPID = "wechat.official.appid";

    public static final String WECHAT_OFFICIAL_SECURITY_ENCRYPT = "wechat.official.security.encrypt";

    public static final String WECHAT_OFFICIAL_SECURITY_ENCODINGKEY = "wechat.official.security.encodingkey";

    public static final String WECHAT_OFFICIAL_SECURITY_TOKEN = "wechat.official.security.token";

    private static EncryptConfig ourInstance;

    public static EncryptConfig getInstance() {
        if (ourInstance == null) {
            ourInstance = new EncryptConfig();
        }
        return ourInstance;
    }

    private boolean mEncypt;

    private String mAppId;

    private String mEncodingKey;

    private String mToken;

    private EncryptConfig() {
        mEncypt = false;
        mAppId = StringUtils.EMPTY;
        mEncodingKey = StringUtils.EMPTY;
        mToken = StringUtils.EMPTY;
    }

    public void set(Properties props) {
        if (props != null) {
            setAppId(WECHAT_OFFICIAL_APPID);
            setToken(props.getProperty(WECHAT_OFFICIAL_SECURITY_TOKEN));
            setEncypt(props.getProperty(WECHAT_OFFICIAL_SECURITY_ENCRYPT, "false"));
            String encodingAesKey = props.getProperty(WECHAT_OFFICIAL_SECURITY_ENCODINGKEY, StringUtils.EMPTY);
            if (!StringUtils.isEmpty(encodingAesKey)) {
                setEncodingKey(encodingAesKey);
            }
        }
    }

    public boolean isEncypt() {
        return mEncypt;
    }

    public String getAppId() {
        return mAppId;
    }

    public String getEncodingKey() {
        return mEncodingKey;
    }

    public String getToken() {
        return mToken;
    }

    public void setEncypt(boolean encypt) {
        mEncypt = encypt;
    }

    public void setEncypt(String encryptText) {
        if (StringUtils.isEmpty(encryptText) &&
                (StringUtils.equalsIgnoreCase("yes", encryptText)
                        || StringUtils.equalsIgnoreCase("true", encryptText))) {
            setEncypt(true);
        } else {
            setEncypt(false);
        }
    }

    public void setAppId(String appId) {
        mAppId = appId;
    }

    public void setEncodingKey(String encodingKey) {
        mEncodingKey = encodingKey;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
