package com.zerone.secondhandmarket.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static Date stringToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }
}
