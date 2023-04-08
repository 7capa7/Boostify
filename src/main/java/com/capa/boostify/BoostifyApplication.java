package com.capa.boostify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoostifyApplication {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(3000);
        SpringApplication.run(BoostifyApplication.class, args);
    }

}
