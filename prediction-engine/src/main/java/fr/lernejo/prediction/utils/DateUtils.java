package fr.lernejo.prediction.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final long ONE_DAY_MILLI_SECONDS = 86400000;

    private DateUtils() {
    }

    public static String oneDayBefore(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date(date.getTime() - ONE_DAY_MILLI_SECONDS);
        return dateFormat.format(newDate);
    }

    public static String twoDaysBefore(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date(date.getTime() - (2 * ONE_DAY_MILLI_SECONDS));
        return dateFormat.format(newDate);
    }
}
