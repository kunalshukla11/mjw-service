package com.mjw.mjwservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MjwServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MjwServiceApplication.class, args);
    }

}
