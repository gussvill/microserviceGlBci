package com.globallogic.microserviceglbci.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class JavaUtils {

    static String FORMAT_DATE = "MMM dd, yyyy hh:mm:ss aa";

    public static String generateDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
        String strDate = formatter.format(date);
        return strDate;
    }

}
