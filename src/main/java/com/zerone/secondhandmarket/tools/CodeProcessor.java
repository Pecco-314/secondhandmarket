package com.zerone.secondhandmarket.tools;

public class CodeProcessor {
    // 编码密码,可自定义
    private static final String ENCODED_PASSWORD = "password";

    /**
     * 编码
     *
     */
    public static String encode(String str) {
        return strToHex(encodeString(str, ENCODED_PASSWORD));
    }

    /**
     * 转换
     *
     */
    private static String encodeString(String str, String password) {
        char[] pwd = password.toCharArray();
        int pwdLen = pwd.length;

        char[] strArray = str.toCharArray();
        for (int i = 0; i < strArray.length; i++) {
            strArray[i] = (char) (strArray[i] ^ pwd[i % pwdLen] ^ pwdLen);
        }
        return new String(strArray);
    }

    private static String strToHex(String s) {
        return bytesToHexStr(s.getBytes());
    }

    private static String bytesToHexStr(byte[] bytesArray) {
        StringBuilder builder = new StringBuilder();
        String hexStr;
        for (byte bt : bytesArray) {
            hexStr = Integer.toHexString(bt & 0xFF);
            if (hexStr.length() == 1) {
                builder.append("0");
            }
            builder.append(hexStr);
        }
        return builder.toString();
    }

    /**
     * 解码
     *
     */
    public static String decode(String str) {
        String hexStr = null;
        try {
            hexStr = hexStrToStr(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (hexStr != null) {
            hexStr = encodeString(hexStr, ENCODED_PASSWORD);
        }
        //提取出有效部分
        return hexStr;
    }

    private static String hexStrToStr(String hexStr) {
        return new String(hexStrToBytes(hexStr));
    }

    private static byte[] hexStrToBytes(String hexStr) {
        String hex;
        int val;
        byte[] btHexStr = new byte[hexStr.length() / 2];
        for (int i = 0; i < btHexStr.length; i++) {
            hex = hexStr.substring(2 * i, 2 * i + 2);
            val = Integer.valueOf(hex, 16);
            btHexStr[i] = (byte) val;
        }
        return btHexStr;
    }

    public static boolean validatePassword(String password,String encryptedPassword){
        return password.equals(decode(encryptedPassword));
    }

    public static boolean validateIdToken(String id,String token){
        return id.equals(decode(token).split("@")[0]);
    }

}
