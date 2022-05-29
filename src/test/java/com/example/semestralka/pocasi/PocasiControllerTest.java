package com.example.semestralka.pocasi;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoController;
import com.example.semestralka.mesto.MestoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
//@WebMvcTest
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
        Pocasi pocasi = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(pocasi);
        Pocasi t =  helper.findAll().get(0);
        Show show =  underTest.showCurrentPocasiFor(mesto.getName(), mesto.getState());
        assertThat(pocasi.getName()).isEqualTo(show.getName());
        assertThat(pocasi.getTemp()-273.15).isEqualTo(show.getTemp());
        assertThat(pocasi.getTime().getHour()).isEqualTo(show.getTime().toLocalDateTime().getHour());
        assertThat(pocasi.getTime().getMinute()).isEqualTo(show.getTime().toLocalDateTime().getMinute());
    }

    @Test
    void showCurrentPocasi() {
        Mesto mesto = new Mesto("Chomutov","CZ");
        helper2.save(mesto);
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(p);
        List<Pocasi> t =  helper.findAll();
        List<Show> shows =  underTest.showCurrentPocasi();
        assertThat(t.get(0).getName()).isEqualTo(shows.get(0).getName());
        assertThat(t.get(0).getTemp()-273.15).isEqualTo(shows.get(0).getTemp());
        assertThat(t.get(0).getTime().getHour()).isEqualTo(shows.get(0).getTime().toLocalDateTime().getHour());
        assertThat(t.get(0).getTime().getMinute()).isEqualTo(shows.get(0).getTime().toLocalDateTime().getMinute());
    }

    @Test
    void showAvgPocasi() {
        Mesto mesto = new Mesto("Chomutov","CZ");
        helper2.save(mesto);
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(p);
        p = new Pocasi("Chomutov",ld,292.0,1000,"CZ");
        helper.save(p);
        List<Show> shows =  underTest.showAvgPocasi(1);
        assertThat(p.getName()).isEqualTo(shows.get(0).getName());
        assertThat(291.0-273.15).isEqualTo(shows.get(0).getTemp());
        assertThat(p.getTime().getHour()).isEqualTo(shows.get(0).getTime().toLocalDateTime().getHour());
        assertThat(p.getTime().getMinute()).isEqualTo(shows.get(0).getTime().toLocalDateTime().getMinute());
    }

    @Test
    void savePocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",null,290.0,1000,"CZ");
        underTest.savePocasi(p);
        assertThat(helper.findAll().contains(new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ")));
    }

    @Test
    void uploadCSV() throws Exception {
        FileInputStream fis = new FileInputStream("src/test/java/com/example/semestralka/pocasi/testCSV.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
        underTest.uploadCSV(multipartFile);
        assertThat(helper.findAll().size()).isGreaterThan(0);
    }


    @Test
    void insertPocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        underTest.insertPocasi(p);
        assertThat(helper.findAll().contains(new Pocasi("Chomutov",ld,290.0,1000,"CZ")));
    }

    @Test
    void deletePocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(p);
        Pocasi r = helper.findAll().get(0);
        underTest.deletePocasi(r);
        assertThat(helper.findAll().size()).isEqualTo(0);
    }

    @Test
    void updatePocasi() {
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(p);
        Pocasi r = helper.findAll().get(0);
        underTest.updatePocasi(r,292.0);
        r.setTemp(292.0);
        assertThat(helper.findAll().get(0)).isEqualTo(r);
    }
}