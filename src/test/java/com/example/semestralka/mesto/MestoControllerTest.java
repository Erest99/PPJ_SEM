package com.example.semestralka.mesto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MestoControllerTest {

    @Autowired
    private MestoController underTest;
    @Autowired
    private MestoRepository helper;

    @AfterEach
    void tearDown()
    {
        helper.deleteAll();
    }


    @Test
    void getCities() {
        Mesto m = new Mesto("Chomutov","CZ");
        helper.save(m);
        List<Mesto> rs =  new ArrayList<>();
        rs.add(m);
        List<Mesto> mesta =  underTest.getCities();
        assertThat(mesta).isEqualTo(rs);

    }

    @Test
    void getCitiesN() {
        List<Mesto> mesta =  underTest.getCities();
        assertThat(mesta).isEqualTo(new ArrayList<>());

    }

    @Test
    void registerNewCity() {
        Mesto m = new Mesto("Chomutov","CZ");
        underTest.registerNewCity(m);
        assertThat(helper.findAll()).isEqualTo(List.of(m));
    }

    @Test
    void deleteMesto() {
        Mesto m = new Mesto("Chomutov","CZ");
        helper.save(m);
        Long id = helper.findAll().get(0).getId();
        underTest.deleteMesto(id);
        assertThat(helper.findAll()).isEqualTo(new ArrayList<>());
    }

    @Test
    void updateMesto() {
        Mesto m = new Mesto("Chomutov","CZ");
        helper.save(m);
        Long id = helper.findAll().get(0).getId();
        underTest.updateMesto(id,"Bratislava","SK");
        assertThat(helper.findAll()).isEqualTo(List.of(new Mesto(id,"Bratislava","SK")));
    }
}