package com.example.semestralka.pocasi;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MainRepository extends MongoRepository<Main,LocalDateTime> {
    Optional<Main> findMainByDate(LocalDateTime date);
}
