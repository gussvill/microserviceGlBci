package com.bci.microservice.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase que configura la herramienta de encriptación Jasypt para que pueda ser utilizada la aplicación para proteger información confidencial.
 */
@Configuration
public class JasyptConfig {

    /**
     * Gets simple string pbe config.
     *
     * @return SimpleStringPBEConfig the simple string pbe config
     */
    public static SimpleStringPBEConfig getSimpleStringPBEConfig() {
        final SimpleStringPBEConfig pbeConfig = new SimpleStringPBEConfig();

        pbeConfig.setPassword("javacodegeek");  //encryptor private key
        pbeConfig.setAlgorithm("PBEWithMD5AndDES");
        pbeConfig.setKeyObtentionIterations("1000");
        pbeConfig.setPoolSize("1");
        pbeConfig.setProviderName("SunJCE");
        pbeConfig.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        pbeConfig.setStringOutputType("base64");

        return pbeConfig;
    }

    /**
     * Encryptor string encryptor.
     *
     * @return the string encryptor
     */
    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor encryptor() {
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(getSimpleStringPBEConfig());

        return pbeStringEncryptor;
    }
}
