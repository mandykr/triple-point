package com.triple.pointservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableJpaAuditing
@SpringBootApplication
public class PointServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointServiceApplication.class, args);
    }

}
