package com.imagine.another_arts.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CommonDateTranslator {
    public static String translateLocalDateTimeToString(LocalDateTime date) {
        return date.getYear() + "년 " +
                date.getMonth().getValue() + "월 " +
                date.getDayOfMonth() + "일 " +
                date.getHour() + "시 " +
                date.getMinute() + "분";
    }

    public static String translateLocalDateToString(LocalDate date) {
        return date.getYear() + "년 " +
                date.getMonth().getValue() + "월 " +
                date.getDayOfMonth() + "일";
    }
}
