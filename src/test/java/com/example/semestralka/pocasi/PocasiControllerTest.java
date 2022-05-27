package com.example.semestralka.pocasi;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoController;
import com.example.semestralka.mesto.MestoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
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
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(p);
        Pocasi t =  helper.findAll().get(0);
        Pocasi pocasi =  underTest.showCurrentPocasiFor(mesto.getName(), mesto.getState());
        assertThat(pocasi).isEqualTo(t);
    }

    @Test
    void showCurrentPocasi() {
        Mesto mesto = new Mesto("Chomutov","CZ");
        helper2.save(mesto);
        LocalDateTime ld = LocalDateTime.now();
        Pocasi p = new Pocasi("Chomutov",ld,290.0,1000,"CZ");
        helper.save(p);
        List<Pocasi> t =  helper.findAll();
        List<Pocasi> pocasi =  underTest.showCurrentPocasi();
        assertThat(pocasi).isEqualTo(t);
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
        List<Pocasi> pocasi =  underTest.showAvgPocasi(1);
        assertThat(pocasi.get(0).getTemp()).isEqualTo(291.0);
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