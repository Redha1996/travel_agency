package fr.lernejo.prediction.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class DateUtilsTest {


    @Test
    void oneDayBeforeTest() {
        final String actual = "2021-12-22";
        Date date = new GregorianCalendar(2021, Calendar.DECEMBER, 23).getTime();
        Assertions.assertEquals(DateUtils.oneDayBefore(date), actual);
    }

    @Test
    void twoDayBeforeTest() {
        final String actual = "2021-12-21";
        Date date = new GregorianCalendar(2021, Calendar.DECEMBER, 23).getTime();
        Assertions.assertEquals(DateUtils.twoDaysBefore(date), actual);
    }

}
