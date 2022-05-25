package com.example.semestralka.mesto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MestoConfig {

//    @Bean
//    CommandLineRunner InsertMesta(MestoRepository repository){
//        return args -> {
//            Mesto Praha =new Mesto(
//                    "Praha",
//                    "CZ",
//                    2.0,
//                    2.0,
//                    "api.openweathermap.org/data/2.5/weather?q=Prague,cz&APPID=95cb3d154ca2cae3b3327826b3bf1c0e"
//
//            );
//            Mesto Liberec = new Mesto(
//                    "Liberec",
//                    "CZ",
//                    1.2,
//                    1.1,
//                    "http://api.openweathermap.org/data/2.5/weather?q=Liberec,cz&APPID=95cb3d154ca2cae3b3327826b3bf1c0e"
//            );
//
//            repository.saveAll(
//                    List.of(Praha,Liberec)
//            );
//        };
//    }
}
