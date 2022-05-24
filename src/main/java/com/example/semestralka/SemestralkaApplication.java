package com.example.semestralka;

import com.example.semestralka.mesto.Mesto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class SemestralkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemestralkaApplication.class, args);
    }



}
