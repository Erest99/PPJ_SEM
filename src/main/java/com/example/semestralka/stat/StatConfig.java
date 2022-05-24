package com.example.semestralka.mesto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MestoConfig {

    @Bean
    CommandLineRunner commandLineRunner(MestoRepository repository){
        return args -> {
            Mesto Praha =new Mesto(
                    "Praha",
                    "CeskaRepublika",
                    2.0,
                    2.0
            );
            Mesto Liberec = new Mesto(
                    "Liberec",
                    "CeskaRepublika",
                    1.2,
                    1.1
            );

            repository.saveAll(
                    List.of(Praha,Liberec)
            );
        };
    }
}
