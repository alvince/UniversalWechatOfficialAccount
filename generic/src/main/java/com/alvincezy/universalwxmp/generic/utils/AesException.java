package com.alvincezy.universalwxmp.generic.utils;

/**
 * Wechat aes exception
 */
@SuppressWarnings("serial")
public class AesException extends Exception {

    public final static int OK = 0;
    public final static int ValidateSignatureError = -40001;
    public final static int ParseXmlError = -40002;
    public final static int ComputeSignatureError = -40003;
    public final static int IllegalAesKey = -40004;
    public final static int ValidateAppidError = -40005;
    public final static int EncryptAESError = -40006;
    public final static int DecryptAESError = -40007;
    public final static int IllegalBuffer = -40008;
    public final static int EncodeBase64Error = -40009;
    public final static int DecodeBase64Error = -40010;
    public final static int GenReturnXmlError = -40011;

    private int code;

    private static String getMessage(int code) {
        switch (code) {
            case ValidateSignatureError:
                return "Signature verification error";
            case ParseXmlError:
                return "Failed to parse xml";
            case ComputeSignatureError:
                return "SHA encryption generated signature fails";
            case IllegalAesKey:
                return "Illegal SymmetricKey";
            case ValidateAppidError:
                return "AppId verification fails";
            case EncryptAESError:
                return "AES encryption failure";
            case DecryptAESError:
                return "AES decryption failure";
            case IllegalBuffer:
                return "Illegal decryption result buffer";
            case EncodeBase64Error:
                return "BASE64 encryption error";
            case DecodeBase64Error:
                return "BASE64 decryption error";
            case GenReturnXmlError:
                return "Xml generation failed";
            default:
                return null; // cannot be
        }
    }

    public int getCode() {
        return code;
    }

    AesException(int code) {
        super(getMessage(code));
        this.code = code;
    }
}
