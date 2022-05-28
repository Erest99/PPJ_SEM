package com.example.semestralka.mesto;

import com.example.semestralka.pocasi.Pocasi;
import com.example.semestralka.pocasi.PocasiController;
import com.example.semestralka.pocasi.PocasiService;
import com.example.semestralka.stat.StatService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class MestoControllerMVCTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private MestoController pocasiController;

    @MockBean
    private MestoService service;

    @MockBean
    private PocasiService pocasiService;

    @MockBean
    private StatService statService;



    @Test
    void getCities() throws Exception {
        List<Mesto> ms = List.of(new Mesto("Chomutov","CZ"));
        Mockito.when(service.getCities()).thenReturn(ms);
        String url = "http://localhost:8080/api/v1/mesto";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        List<Mesto> mesta =  mapper.readValue(resp, new TypeReference<List<Mesto>>(){});
        assertThat(mesta).isEqualTo(ms);

    }


    @Test
    void registerNewCity() throws Exception {
        Mesto m = new Mesto("Chomutov","CZ");
        String url = "http://localhost:8080/api/v1/mesto";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(m);

        mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).param("name", "Liberec")).andExpect(status().isOk()).andReturn();

    }

    @Test
    void deleteMesto() throws Exception {
        Mesto m = new Mesto("Chomutov","CZ");
        String url = "http://localhost:8080/api/v1/mesto/1";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(m);

        mvc.perform(delete(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void updateMesto() throws Exception {
        Mesto m = new Mesto("Chomutov","CZ");
        String url = "http://localhost:8080/api/v1/mesto/1";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(m);

        mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
    }
}