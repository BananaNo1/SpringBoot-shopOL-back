package com.wust.graproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author ManMan
 */
@SpringBootApplication
@EnableAsync
public class GraprojectApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(GraprojectApplication.class, args);
    }

}

