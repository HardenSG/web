package com.example.demo.utils;

import org.springframework.util.DigestUtils;

public class Md5Utils {
    private static final String salt = "edg qwerdf win";
    public static String getMD5(String password) {
        //  将用户键入的密码与盐进行字符串拼接，
        //  拼接的方式有多种，盐的定义也有多种，自定义即可
        String finalPwd = password+ salt;
        //  调用该类下返回值为String的方法，并将finalPwd转换为byte[]类型
        String Md5Password = DigestUtils.md5DigestAsHex(finalPwd.getBytes());
          return Md5Password;

    }

}
