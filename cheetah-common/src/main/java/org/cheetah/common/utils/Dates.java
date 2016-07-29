package org.cheetah.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Created by maxhuang on 2016/7/1.
 */
public final class Dates {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "HH:mm:ss";

    private Dates(){}

    public static String now() {
        return now(DEFAULT_DATE_TIME_FORMAT);
    }

    public static String now(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String time() {
        return time(TIME_FORMAT);
    }

    public static String time(String format) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String date() {
        return date(DATE_FORMAT);
    }

    public static String date(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static int year(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format)).getYear();
    }

    public static int month(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format)).getMonthValue();
    }

    public static int day(String date, String format) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format)).getDayOfMonth();
    }

    public static int diffMonths(int year, int month, int day) {
        LocalDate dateTime = LocalDate.of(year, Month.of(month), day);
        Period period = Period.between(LocalDate.now(), dateTime);
        return period.getMonths();
    }


}
