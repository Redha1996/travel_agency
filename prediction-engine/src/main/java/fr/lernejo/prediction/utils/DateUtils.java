package fr.lernejo.prediction.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private DateUtils() {
    }

    public static String oneDayBefore(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date(date.getTime() - 86400000);
        return dateFormat.format(newDate);
    }

    public static String twoDaysBefore(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date(date.getTime() - (2 * 86400000));
        return dateFormat.format(newDate);
    }
}
