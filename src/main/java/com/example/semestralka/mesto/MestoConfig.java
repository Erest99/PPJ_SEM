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
                    1L,
                    "Praha",
                    "CeskaRepublika"
            );
            Mesto Liberec = new Mesto(
                    "Liberec",
                    "CeskaRepublika"
            );

            repository.saveAll(
                    List.of(Praha,Liberec)
            );
        };
    }
}
