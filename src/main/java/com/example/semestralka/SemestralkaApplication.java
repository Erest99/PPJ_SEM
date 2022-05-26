package com.example.semestralka;

import com.example.semestralka.mesto.Mesto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class SemestralkaApplication {



    public static void main(String[] args) {
        SpringApplication.run(SemestralkaApplication.class, args);

        //LocalDateTime l = LocalDateTime.now();
    }



}
