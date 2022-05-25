package com.example.semestralka.stat;

import com.example.semestralka.mesto.Mesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<Stat,String> {

}
