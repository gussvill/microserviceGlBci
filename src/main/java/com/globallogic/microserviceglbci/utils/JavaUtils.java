package com.globallogic.microserviceglbci.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class JavaUtils {

    static String FORMAT_DATE = "MMM dd, yyyy hh:mm:ss a";

    public static String formattedDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE));
    }

}
