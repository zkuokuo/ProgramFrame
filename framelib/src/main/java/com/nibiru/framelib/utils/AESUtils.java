package com.nibiru.framelib.utils;
import java.util.regex.Pattern;

/**
 * @Description: AES加密解密工具类
 */
public class AESUtils {
    private final static String HEX = "0123456789abcdef";

    /**
     * 加密
     */
    public static String encrypt(String src) {
        return toHex(src).replaceAll("2", "s");
    }

    /**
     * 解密
     */
    public static String decrypt(String encrypted) {
        return fromHex(encrypted.replace("s", "2"));
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        byte[] bytes = toByte(hex);
        if (bytes == null) {
            return "";
        } else {
            return new String(bytes);
        }
    }

    //判断二维码是不是正确的
    public static boolean checkString(String hexString) {
        return Pattern.matches("[0-9a-f]*", hexString);
    }

    public static byte[] toByte(String hexString) {
        if (checkString(hexString)) {
            int len = hexString.length() / 2;
            byte[] result = new byte[len];
            for (int i = 0; i < len; i++) {
                result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
            }
            return result;
        } else {
            return null;
        }
    }

    //二进制转字符,转成了16进制
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for ( int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}