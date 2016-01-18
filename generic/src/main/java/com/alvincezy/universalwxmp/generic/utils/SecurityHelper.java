package com.alvincezy.universalwxmp.generic.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class SecurityHelper {

    private static final char[] DIGIT_HEX = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E'};

    public static String getSHA1Digest(String text) throws NoSuchAlgorithmException {
        StringBuilder sha1Str = new StringBuilder();
        MessageDigest digest = MessageDigest.getInstance("SHA-1");

        for (byte b : digest.digest(text.getBytes())) {
            sha1Str.append(convert2Hex(b));
        }

        return sha1Str.toString();
    }

    private static String convert2Hex(byte b) {
        char[] tmpArr = new char[2];
        tmpArr[0] = DIGIT_HEX[(b >>> 4) & 0xFF];
        tmpArr[1] = DIGIT_HEX[b & 0xFF];
        return new String(tmpArr);
    }
}
