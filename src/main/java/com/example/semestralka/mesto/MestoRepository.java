package com.example.semestralka.mesto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MestoRepository extends JpaRepository<Mesto,Long> {

    //SELECT * FROM mesto WHERE name = ? AND state = ?
    //@Query("SELECT s FROM Mesto s WHERE s.name  = ?1")
    Optional<Mesto> findMestoByNameAndState(String name,String state);
}
