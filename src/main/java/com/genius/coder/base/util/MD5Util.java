package com.genius.coder.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/6
 */
public final class MD5Util {
    private static char[] hexdigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getRowMD5(String inStr) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception var11) {
            System.out.println(var11.toString());
            var11.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0; i < charArray.length; ++i) {
            byteArray[i] = (byte)charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        byte[] var6 = md5Bytes;
        int var7 = md5Bytes.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            byte md5Byte = var6[var8];
            int val = md5Byte & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static String getMD5(File file) {
        FileInputStream fis = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            boolean var4 = true;

            int length;
            while((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }

            byte[] b = md.digest();
            String var6 = byteToHexString(b);
            return var6;
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return null;
    }

    private static String byteToHexString(byte[] tmp) {
        char[] str = new char[32];
        int k = 0;

        for(int i = 0; i < 16; ++i) {
            byte byte0 = tmp[i];
            str[k++] = hexdigits[byte0 >>> 4 & 15];
            str[k++] = hexdigits[byte0 & 15];
        }

        String s = new String(str);
        return s;
    }

    public static String Md5SaltEncode(String rawPass, String passType, String salt) {
        String result = null;

        try {
            MessageDigest md = MessageDigest.getInstance(passType);
            result = byteToHexString(md.digest(mergePasswordAndSalt(rawPass, salt).getBytes("utf-8")));
        } catch (Exception var5) {
            ;
        }

        return result;
    }

    public static String mergePasswordAndSalt(String password, String salt) {
        if (password == null) {
            password = "";
        }

        return salt != null && !"".equals(salt) ? password + "{" + salt.toString() + "}" : password;
    }

    public static String getMd5() {
        return UUID.randomUUID().toString();
    }

    private MD5Util() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

