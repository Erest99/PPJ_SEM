package com.example.semestralka.mesto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MestoRepository extends JpaRepository<Mesto,Long> {

    Optional<Mesto> findMestoByNameAndState(String name,String state);
}
