package com.example.demo.utils;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author Chu
 * @create 2022-03-29-15:43
 */
public class DateUtils {
    public static String parseDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

}
