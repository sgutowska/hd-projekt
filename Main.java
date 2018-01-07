package com.uek.etl;

import com.uek.etl.controller.MainPageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.uek.etl.repository.ReviewRepository;


@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}