package com.globallogic.microserviceglbci.utils;

import com.globallogic.microserviceglbci.config.JasyptConfig;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

@Slf4j
public class EncryptDecryptPwd {

    private static final String DB_USERNAME = "product";
    private static final String DB_PWD = "password!234#";

    public static void main(String[] args) {
        encryptKey(DB_USERNAME);
        encryptKey(DB_PWD);

//        decryptKey("BJQ0oyj1UVjIbV1Z2wUzlA==");
//        decryptKey("fl8OJudxGl4GXBnbDD140Ujo5kDAZFOm");
    }

    private static void encryptKey(final String plainKey) {
        final SimpleStringPBEConfig pbeConfig = JasyptConfig.getSimpleStringPBEConfig();
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(pbeConfig);

        log.info("Encrypted key = {}", pbeStringEncryptor.encrypt(plainKey));
    }

    private static void decryptKey(final String encryptedKey) {
        final SimpleStringPBEConfig pbeConfig = JasyptConfig.getSimpleStringPBEConfig();
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(pbeConfig);

        log.info("Decrypted key = {}", pbeStringEncryptor.decrypt(encryptedKey));
    }
}
