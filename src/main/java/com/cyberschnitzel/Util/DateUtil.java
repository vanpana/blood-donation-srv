package com.cyberschnitzel.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private final static SimpleDateFormat europeanSimpleDateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public static Date getDate(String date) {
        try {
            return europeanSimpleDateTimeFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Failed to parse the date");
            return null;
        }
    }

    public static String getString(Date date) {
        return europeanSimpleDateTimeFormat.format(date);
    }
}
