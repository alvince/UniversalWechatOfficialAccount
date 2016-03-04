package com.alvincezy.universalwxmp.generic.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/14.
 *
 * @author alvince.zy@gmail.com
 */
public class SecurityHelper {

    private static final char[] DIGIT_HEX = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final String RANDOM_BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final String ALGORITHM_AES = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NoPadding";

    private static Charset CHARSET = Charset.forName("UTF-8");

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
        tmpArr[0] = DIGIT_HEX[(b >>> 4) & 0x0F];
        tmpArr[1] = DIGIT_HEX[b & 0x0F];
        return new String(tmpArr);
    }

    private static String getRandomStr() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(RANDOM_BASE.length());
            sb.append(RANDOM_BASE.charAt(number));
        }
        return sb.toString();
    }

    private static byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }
}
