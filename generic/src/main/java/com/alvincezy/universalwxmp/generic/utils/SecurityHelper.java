package com.alvincezy.universalwxmp.generic.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    public static String getAESCiphertext(byte[] aesKey, String text) {
        ByteGroup byteCollector = new ByteGroup();
        String randomStr = getRandomStr();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = text.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        //byte[] appidBytes = appId.getBytes(CHARSET);

        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, ALGORITHM_AES);
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            //byte[] encrypted = cipher.doFinal(text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
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

    static class ByteGroup {
        ArrayList<Byte> byteContainer = new ArrayList<Byte>();

        public byte[] toBytes() {
            byte[] bytes = new byte[byteContainer.size()];
            for (int i = 0; i < byteContainer.size(); i++) {
                bytes[i] = byteContainer.get(i);
            }
            return bytes;
        }

        public ByteGroup addBytes(byte[] bytes) {
            for (byte b : bytes) {
                byteContainer.add(b);
            }
            return this;
        }

        public int size() {
            return byteContainer.size();
        }
    }
}
