package com.example.semestralka.stat;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoController;
import com.example.semestralka.mesto.MestoService;
import com.example.semestralka.pocasi.PocasiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class StatControllerMVCTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private MestoController pocasiController;

    @MockBean
    private MestoService mestoService;

    @MockBean
    private PocasiService pocasiService;

    @MockBean
    private StatService service;


    @Test
    void getCountries() throws Exception {
        List<Stat> ss = List.of(new Stat("CZ","CeskaRepublika"));
        Mockito.when(service.getStates()).thenReturn(ss);
        String url = "http://localhost:8080/api/v1/stat";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        List<Stat> staty =  mapper.readValue(resp, new TypeReference<List<Stat>>(){});
        assertThat(staty).isEqualTo(ss);
    }

    @Test
    void registerNewState() throws Exception {
        Stat s = new Stat("CZ","CeskaRepublika");
        String url = "http://localhost:8080/api/v1/stat";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(s);

        mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteStat() throws Exception {
        Stat s = new Stat("CZ","CeskaRepublika");
        String url = "http://localhost:8080/api/v1/stat/1";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(s);

        mvc.perform(delete(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void updateStat() throws Exception {
        Stat s = new Stat("CZ","CeskaRepublika");
        String url = "http://localhost:8080/api/v1/stat/CZ";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(s);

        mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).param("name","CR")).andExpect(status().isOk()).andReturn();
    }
}