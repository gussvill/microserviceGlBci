package com.globallogic.microserviceglbci;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//lombok annotation
@Slf4j
//spring annotation
@SpringBootApplication
//spring jasypt annotation
//helps to make the application understand the encryptable properties
//across the environment
@EnableEncryptableProperties
public class BciMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BciMicroserviceApplication.class, args);
    }

}
