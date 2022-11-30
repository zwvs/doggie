package com.zw.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss:SS";

    public static long now() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String formatDefault(long mills) {
        return new SimpleDateFormat(DATA_FORMAT).format(mills);
    }
}
