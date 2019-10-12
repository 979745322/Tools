package com.erning.common.utils;

/**
 * Created by 二宁 on 2017/11/23.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密算法
 * */
public class MD5_Calc {

    public static String Md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    // 对密码进行MD5加密
    public static String EncodePassword(String name, String password) {
        String StrSource = name + password + "lencity";
        String PlainText = "";
        try {
            PlainText = Md5(StrSource);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return PlainText;
    }
}