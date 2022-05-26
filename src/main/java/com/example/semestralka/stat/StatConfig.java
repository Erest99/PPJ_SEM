package com.example.semestralka.stat;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StatConfig {

    @Bean
    CommandLineRunner InsertCR(StatRepository repository){
        return args -> {
            Stat CeskaRepublika =new Stat(

                        "CZ",
                        "CeskaRepublika"

            );

            repository.saveAll(
                    List.of(CeskaRepublika)
            );
        };
    }
}
