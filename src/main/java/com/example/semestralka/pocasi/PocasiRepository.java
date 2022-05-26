package com.example.semestralka.pocasi;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PocasiRepository extends MongoRepository<Pocasi,String> {
    List<Pocasi> findByTimeBefore(LocalDateTime time);
    List<Pocasi> findPocasiByNameAndSys(String name, Sys sys);
    Pocasi findPocasiByNameAndTimeAndSys(String name, LocalDateTime time, Sys sys );
}
