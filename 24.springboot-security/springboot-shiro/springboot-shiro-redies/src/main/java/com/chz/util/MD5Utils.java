package com.chz.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * MD5加密工具类
 */
public class MD5Utils {
    //加密方式可以时SHA1或是MD5
    private static final String ALGORITHM_NAME = "MD5";
    private static final int HASH_ITERATIONS = 5;
    private static final String SALT = "chz";

    //加密方式,加密对象,盐值,加密次数
    public static String encrypt(String password, String username) {
        SimpleHash hash = new SimpleHash(ALGORITHM_NAME, password, username, HASH_ITERATIONS);
        return hash.toHex();
//        return hash.toString();
    }

    public static String encrypt(String password) {
        SimpleHash hash = new SimpleHash(ALGORITHM_NAME, password, SALT, HASH_ITERATIONS);
        return hash.toHex();
//        return hash.toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123", "zsf"));
    }
}
