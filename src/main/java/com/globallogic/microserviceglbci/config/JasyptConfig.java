package com.globallogic.microserviceglbci.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

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

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor encryptor() {
        final PooledPBEStringEncryptor pbeStringEncryptor = new PooledPBEStringEncryptor();
        pbeStringEncryptor.setConfig(getSimpleStringPBEConfig());

        return pbeStringEncryptor;
    }
}

/*
    La clase JasyptConfig es una clase de configuración de Spring que utiliza la librería Jasypt para encriptar y desencriptar cadenas de texto.
    Jasypt es una librería que ofrece diversos algoritmos de cifrado y herramientas para proteger información confidencial en aplicaciones Java.

    En esta clase, se define un método estático getSimpleStringPBEConfig() que devuelve una instancia de SimpleStringPBEConfig, la cual es una clase de Jasypt que configura los parámetros de cifrado de texto.
    En este método se establece la contraseña utilizada para encriptar y desencriptar la información, el algoritmo de cifrado, la cantidad de iteraciones, el tamaño del pool de cifrado, el proveedor de seguridad, el generador de saltos y el tipo de salida.

    Además, se define un Bean de Spring llamado "jasyptStringEncryptor" que utiliza la configuración anterior y devuelve una instancia de PooledPBEStringEncryptor,
    la cual es una clase de Jasypt que se encarga del cifrado y desencriptado de cadenas de texto utilizando la configuración definida en getSimpleStringPBEConfig().

    En resumen, esta clase configura la herramienta de encriptación Jasypt para que pueda ser utilizada en una aplicación de Spring para proteger información confidencial.
 */