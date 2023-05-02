package com.globallogic.microserviceglbci.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyAppProperties {

    @Value("${myapp.formatDate}")
    private String formatDate;

    @Value("${myapp.expirationTokenMs}")
    private int expirationTokenMs;

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public int getExpirationTokenMs() {
        return expirationTokenMs;
    }

    public void setExpirationTokenMs(int expirationTokenMs) {
        this.expirationTokenMs = expirationTokenMs;
    }

    @Override
    public String toString() {
        return "MyAppProperties{" +
                "formatDate='" + formatDate + '\'' +
                ", expirationDateMs=" + expirationTokenMs +
                '}';
    }
}