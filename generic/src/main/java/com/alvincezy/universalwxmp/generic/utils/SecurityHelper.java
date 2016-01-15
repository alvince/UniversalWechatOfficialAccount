package com.alvincezy.universalwxmp.generic.utils;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class SecurityHelper {

    public static boolean validate(String signature, String token, String timestamp, String nonce) {
        String[] tmpArr = new String[] {token, timestamp, nonce};
        Arrays.sort(tmpArr);
        String tmpStr = Arrays.toString(tmpArr);

        //String encriptStr =
        return true;
    }
}
