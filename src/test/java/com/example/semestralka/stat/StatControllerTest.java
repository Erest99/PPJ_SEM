package com.example.semestralka.stat;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoController;
import com.example.semestralka.mesto.MestoRepository;
import com.example.semestralka.mesto.MestoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatControllerTest {

    @Autowired
    private StatController underTest;
    @Autowired
    private StatRepository helper;

    @AfterEach
    void tearDown()
    {
        helper.deleteAll();
    }

    @Test
    void getCountries() {
        Stat s = new Stat("CZ","Česká Republika");
        helper.save(s);
        List<Stat> ss =  new ArrayList<>();
        ss.add(s);
        List<Stat> staty =  underTest.getCountries();
        assertThat(staty).isEqualTo(ss);
    }

    @Test
    void registerNewState() {
        Stat s = new Stat("CZ","Česká Republika");
        underTest.registerNewState(s);
        assertThat(helper.findAll()).isEqualTo(List.of(s));
    }

    @Test
    void deleteStat() {
        Stat s = new Stat("CZ","Česká Republika");
        helper.save(s);
        underTest.deleteStat("CZ");
        assertThat(helper.findAll()).isEqualTo(new ArrayList<>());
    }

    @Test
    void updateStat() {
        Stat s = new Stat("CZ","Slovensko");
        helper.save(s);
        underTest.updateStat("CZ","Česká Republika");
        assertThat(helper.findAll()).isEqualTo(List.of(new Stat("CZ","Česká Republika")));
    }
}