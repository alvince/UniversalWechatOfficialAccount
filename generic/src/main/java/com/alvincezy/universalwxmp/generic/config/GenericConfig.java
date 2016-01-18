package com.alvincezy.universalwxmp.generic.config;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Object> mChildMap;

    private GenericConfig() {
    }

    public Object get(String key) {
        return getChildMap().get(key);
    }

    public void put(String key, Object param) {
        getChildMap().put(key, param);
    }

    private Map<String, Object> getChildMap() {
        if (mChildMap == null) {
            mChildMap = new HashMap<String, Object>();
        }
        return mChildMap;
    }
}
