package com.example.schedule.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换操作类
 */
public class DateHandler {

    private static DateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

    public static String parseDatetoString(Date date) {
        return dateFormat.format(date);
    }

    public static Date parseStringtoDate(String string) {
        Date date = new Date();
        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

