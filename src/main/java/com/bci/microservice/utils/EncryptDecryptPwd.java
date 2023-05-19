package com.bci.microservice.utils;

import com.bci.microservice.config.JasyptConfig;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * clase de utilidad que se utiliza para cifrar y descifrar contraseñas en una aplicación Java utilizando el framework Jasypt
 */
@Slf4j
public class EncryptDecryptPwd {

    private static final String DB_USERNAME = "root";
    private static final String DB_PWD = "guss2020";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        encryptKey(DB_USERNAME);
        encryptKey(DB_PWD);

//        decryptKey("ENC(LTuzsowYJ7DHzacC0mkGNg==)");
//        decryptKey("ENC(NBX2RHYV6K3Gf1eHI93Xad1FwdN0ED9u)");
    }

    /**
     * El método `encryptKey()` toma una cadena de texto como argumento y utiliza Jasypt para cifrar la cadena.
     *
     * @param plainKey the plain key
     */
    private static void encryptKey(final String plainKey) {
        final SimpleStringPBEConfig pbeConfig = JasyptConfig.getSimpleStringPBEConfig();
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(pbeConfig);

        log.info("Encrypted key = {}", pbeStringEncryptor.encrypt(plainKey));
    }

    /**
     * El método `decryptKey()` toma una cadena cifrada como argumento y utiliza Jasypt para descifrar la cadena.
     *
     * @param encryptedKey the encrypted key
     */
    private static void decryptKey(final String encryptedKey) {
        final SimpleStringPBEConfig pbeConfig = JasyptConfig.getSimpleStringPBEConfig();
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(pbeConfig);

        log.info("Decrypted key = {}", pbeStringEncryptor.decrypt(encryptedKey));
    }
}