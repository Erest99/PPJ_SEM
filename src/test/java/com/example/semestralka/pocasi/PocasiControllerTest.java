package com.example.semestralka.pocasi;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoController;
import com.example.semestralka.mesto.MestoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PocasiControllerTest {

    @Autowired
    private PocasiController underTest;
    @Autowired
    private PocasiRepository helper;
    @Autowired
    private MestoRepository helper2;

    @AfterEach
    void tearDown()
    {
        helper.deleteAll();
        helper2.deleteAll();
    }

    @Test
    void showCurrentPocasiFor() {
        Mesto mesto = new Mesto("Chomutov","CZ");
        helper2.save(mesto);
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",new Main(290.0,1000),new Sys("CZ"),ld);
        helper.save(p);
        List<Pocasi> ps =  helper.findAll();
        Pocasi pocasi =  underTest.showCurrentPocasiFor(mesto.getName(), mesto.getState());
        assertThat(pocasi.getName()).isEqualTo(ps.get(0).getName());
        assertThat(pocasi.getMain().getTemp()).isEqualTo(ps.get(0).getMain().getTemp());
    }

    @Test
    void showCurrentPocasi() {
        helper2.save(new Mesto("Chomutov","CZ"));
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",new Main(290.0,1000),new Sys("CZ"),ld);
        helper.save(p);
        List<Pocasi> ps =  helper.findAll();
        List<Pocasi> pocasi =  underTest.showCurrentPocasi();
        assertThat(pocasi.get(0).getName()).isEqualTo(ps.get(0).getName());
        assertThat(pocasi.get(0).getMain().getTemp()).isEqualTo(ps.get(0).getMain().getTemp());
    }

    @Test
    void showAvgPocasi() {
        helper2.save(new Mesto("Chomutov","CZ"));
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",new Main(290.0,1000),new Sys("CZ"),ld);
        helper.save(p);
        List<Pocasi> ps =  helper.findAll();
        List<Pocasi> pocasi =  underTest.showAvgPocasi(1);
        assertThat(pocasi.get(0).getName()).isEqualTo(ps.get(0).getName());
        assertThat(pocasi.get(0).getMain().getTemp()).isEqualTo(ps.get(0).getMain().getTemp());
    }

    @Test
    void savePocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",new Main(290.0,1000),new Sys("CZ"),ld);
        underTest.savePocasi(p);
        assertThat(helper.findAll().size()).isEqualTo(1);
    }

    @Test
    void uploadCSV() {
    }

    @Test
    void getAllPocasiInCsv() {
    }

    @Test
    void insertPocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",new Main(290.0,1000),new Sys("CZ"),ld);
        underTest.savePocasi(p);
        assertThat(helper.findAll().size()).isEqualTo(1);
    }

    @Test
    void deletePocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",new Main(290.0,1000),new Sys("CZ"),ld);
        helper.save(p);
        underTest.deletePocasi(p);
        assertThat(helper.findAll().size()).isEqualTo(0);
    }

    @Test
    void updatePocasi() {
    }
}