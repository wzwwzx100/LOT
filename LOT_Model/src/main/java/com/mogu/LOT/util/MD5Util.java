package com.mogu.LOT.util;

import java.security.MessageDigest;

public class MD5Util {

    public static String toMD5(String msg){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(msg.getBytes("UTF-8"));
            byte[] md5Array = md5.digest();
            return  toHex(md5Array);
        }catch (Exception e){
            return "";
        }
    }

    private static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }


    public static void main(String[] args){
        String str = "2018022715514357103123456789012101112345678";
        System.out.println(MD5Util.toMD5(str).substring(0,8));
    }
}
