package com.globallogic.microserviceglbci.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Date Utility class");
    }

    public static String formattedDate(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static Date expirationDate(int expirationDateMs) {
        return new Date(new Date().getTime() + expirationDateMs);
    }

}
