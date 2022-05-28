package com.example.semestralka.pocasi;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
import com.example.semestralka.mesto.MestoService;
import com.example.semestralka.stat.StatService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebMvcTest
@RequestMapping()
@AutoConfigureMockMvc
class PocasiControllerMVCTest {



    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private PocasiController pocasiController;

    @MockBean
    private PocasiService service;

    @MockBean
    private MestoService mestoService;

    @MockBean
    private StatService statService;



    @Test
    void showCurrentPocasiFor() throws Exception {

        Pocasi p = new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ");
        Mockito.when(service.getPocasiInCity("Chomutov","CZ")).thenReturn(p);
        String url = "http://localhost:8080/api/v1/pocasi/current_Chomutov_CZ";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        Pocasi pocasi = mapper.readValue(resp, Pocasi.class);
        assertThat(pocasi).isEqualTo(p);
    }

    @Test
    void showCurrentPocasi() throws Exception {
        List<Pocasi> ps = List.of(new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ"));
        Mockito.when(service.getCurrentPocasi()).thenReturn(ps);
        String url = "http://localhost:8080/api/v1/pocasi/current";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        List<Pocasi> pocasi =  mapper.readValue(resp, new TypeReference<List<Pocasi>>(){});
        assertThat(pocasi).isEqualTo(ps);



    }

    @Test
    void showAvgPocasi() throws Exception {
        List<Pocasi> ps = List.of(new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ"));
        Mockito.when(service.getAVGofPocasi(1)).thenReturn(ps);
        String url = "http://localhost:8080/api/v1/pocasi/avg_1";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        List<Pocasi> pocasi =  mapper.readValue(resp, new TypeReference<List<Pocasi>>(){});
        assertThat(pocasi).isEqualTo(ps);
    }

    @Test
    void savePocasi() throws Exception {

    }

    @Test
    void uploadCSV() throws Exception {

    }


    @Test
    void insertPocasi() {

    }

    @Test
    void deletePocasi() {

    }

    @Test
    void updatePocasi() {

    }
}