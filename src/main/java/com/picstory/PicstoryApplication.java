package com.picstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PicstoryApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name",
                "application, application-db, application-login");
        SpringApplication.run(PicstoryApplication.class, args);
    }

}
