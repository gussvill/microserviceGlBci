package com.globallogic.microserviceglbci.utils;

import com.globallogic.microserviceglbci.config.JasyptConfig;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

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

    public static String encryptKey(final String plainKey) {
        final SimpleStringPBEConfig pbeConfig = JasyptConfig.getSimpleStringPBEConfig();
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(pbeConfig);

        log.info("Encrypted key = {}", pbeStringEncryptor.encrypt(plainKey));

        return pbeStringEncryptor.encrypt(plainKey);
    }

    //decrypt the encrypted text
    public static String decryptKey(final String encryptedKey) {
        final SimpleStringPBEConfig pbeConfig = JasyptConfig.getSimpleStringPBEConfig();
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(pbeConfig);

        log.info("Decrypted key = {}", pbeStringEncryptor.decrypt(encryptedKey));

        return pbeStringEncryptor.decrypt(encryptedKey);
    }


}
