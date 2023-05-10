package com.globallogic.microserviceglbci.utils;

import com.globallogic.microserviceglbci.config.JasyptConfig;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

//  se utiliza para encriptar y desencriptar contraseñas y otros datos sensibles. La clase utiliza la biblioteca Jasypt para la encriptación y desencriptación de datos.
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

/*
 Este código es una clase de utilidad que se utiliza para cifrar y descifrar contraseñas en una aplicación Java utilizando el framework Jasypt.

    La anotación `@Slf4j` indica que se está utilizando el framework `Lombok` para generar código que permite usar las utilidades de logging, como `log.info()` en este caso.

    La clase tiene dos métodos: `encryptKey()` y `decryptKey()`. El método `encryptKey()` toma una cadena de texto como argumento y utiliza Jasypt para cifrar la cadena.
    El método `decryptKey()` toma una cadena cifrada como argumento y utiliza Jasypt para descifrar la cadena.

    En este código, se define dos constantes, `DB_USERNAME` y `DB_PWD`, que representan el nombre de usuario y la contraseña de una base de datos, respectivamente.
    Luego, se llama a los métodos `encryptKey()` y `decryptKey()` para cifrar y descifrar estos valores.

    Es importante destacar que esta clase no debe usarse para almacenar contraseñas en texto plano. El propósito de esta clase es cifrar las contraseñas y almacenar las versiones cifradas en la aplicación o en archivos de configuración.

    En resumen, esta clase se utiliza para cifrar y descifrar contraseñas utilizando el framework Jasypt, y puede ser útil en aplicaciones Java que necesiten almacenar contraseñas de forma segura.
 */
