package com.example.semestralka.pocasi;

import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
import com.example.semestralka.mesto.MestoService;
import com.example.semestralka.stat.StatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WebMvcTest
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

        Show show = new Show("Chomutov", Timestamp.valueOf(LocalDateTime.now()),290.0,1000,"CZ");
        Mockito.when(service.getPocasiInCity("Chomutov","CZ")).thenReturn(show);
        String url = "http://localhost:8080/api/v1/pocasi/current_Chomutov_CZ";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        Pocasi pocasi = mapper.readValue(resp, Pocasi.class);
        assertThat(pocasi.getName()).isEqualTo(show.getName());
        assertThat(pocasi.getTemp()).isEqualTo(show.getTemp());
        assertThat(pocasi.getTime().getHour()).isEqualTo(show.getTime().toLocalDateTime().minusHours(2).getHour());
        assertThat(pocasi.getTime().getMinute()).isEqualTo(show.getTime().toLocalDateTime().getMinute());

    }

    @Test
    void showCurrentPocasiForF() throws Exception {

        Mockito.when(service.getPocasiInCity("Chomutov","SK")).thenReturn(null);
        String url = "http://localhost:8080/api/v1/pocasi/current_Chomutov_SK";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isForbidden()).andReturn();

    }

    @Test
    void showCurrentPocasi() throws Exception {
        List<Show> shows = List.of(new Show("Chomutov", Timestamp.valueOf(LocalDateTime.now()),290.0,1000,"CZ"));
        Mockito.when(service.getCurrentPocasi()).thenReturn(shows);
        String url = "http://localhost:8080/api/v1/pocasi/current";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        List<Pocasi> pocasi =  mapper.readValue(resp, new TypeReference<List<Pocasi>>(){});
        Pocasi p = pocasi.get(0);
        assertThat(p.getName()).isEqualTo(shows.get(0).getName());
        assertThat(p.getTemp()).isEqualTo(shows.get(0).getTemp());
        assertThat(p.getTime().getHour()).isEqualTo(shows.get(0).getTime().toLocalDateTime().minusHours(2).getHour());
        assertThat(p.getTime().getMinute()).isEqualTo(shows.get(0).getTime().toLocalDateTime().getMinute());



    }



    @Test
    void showAvgPocasi() throws Exception {
        List<Show> shows = List.of(new Show("Chomutov", Timestamp.valueOf(LocalDateTime.now()),290.0,1000,"CZ"));
        Mockito.when(service.getAVGofPocasi(1)).thenReturn(shows);
        String url = "http://localhost:8080/api/v1/pocasi/avg_1";

        MvcResult result = mvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String resp = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        List<Pocasi> pocasi =  mapper.readValue(resp, new TypeReference<List<Pocasi>>(){});
        Pocasi p = pocasi.get(0);
        assertThat(p.getName()).isEqualTo(shows.get(0).getName());
        assertThat(p.getTemp()).isEqualTo(shows.get(0).getTemp());
        assertThat(p.getTime().getHour()).isEqualTo(shows.get(0).getTime().toLocalDateTime().minusHours(2).getHour());
        assertThat(p.getTime().getMinute()).isEqualTo(shows.get(0).getTime().toLocalDateTime().getMinute());
    }

    @Test
    void savePocasi() throws Exception {
        Pocasi p = new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ");
        String url = "http://localhost:8080/api/v1/pocasi";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(p);

        mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                            .content(requestJson)).andExpect(status().isOk()).andReturn();

    }

    @Test
    void uploadCSV() throws Exception {
        String url = "http://localhost:8080/api/v1/pocasi/upload";
        FileInputStream fis = new FileInputStream("src/test/java/com/example/semestralka/pocasi/testCSV.csv");
        MockMultipartFile file = new MockMultipartFile("file", fis);
        mvc.perform(multipart(url).file(file)).andExpect(status().isOk());

    }

    @Test
    void uploadCSVF() throws Exception {
        String url = "http://localhost:8080/api/v1/pocasi/upload";
        FileInputStream fis = new FileInputStream("src/test/java/com/example/semestralka/pocasi/testCSV.csv");
        mvc.perform(multipart(url)).andExpect(status().isBadRequest());

    }

    @Test
    void getAllPocasiInCsv() throws Exception {
        String url = "http://localhost:8080/api/v1/pocasi/download";
        HttpServletResponse response = mock(HttpServletResponse.class);
        mvc.perform(post(url)).andExpect(status().isOk());

    }




    @Test
    void insertPocasi() throws Exception {
        Pocasi p = new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ");
        String url = "http://localhost:8080/api/v1/pocasi/add";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(p);

        mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void insertPocasiF() throws Exception {
        String url = "http://localhost:8080/api/v1/pocasi/add";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(null);

        mvc.perform(post(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void deletePocasi() throws Exception {
        Pocasi p = new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ");
        String url = "http://localhost:8080/api/v1/pocasi/del";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(p);

        mvc.perform(delete(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void deletePocasiF() throws Exception {
        String url = "http://localhost:8080/api/v1/pocasi/del";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(null);

        mvc.perform(delete(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void updatePocasi() throws Exception {
        Pocasi p = new Pocasi("Chomutov",LocalDateTime.now(),290.0,1000,"CZ");
        String url = "http://localhost:8080/api/v1/pocasi/upd";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(p);

        mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).param("temp","290.0")).andExpect(status().isOk()).andReturn();
    }

    @Test
    void updatePocasiF() throws Exception {
        String url = "http://localhost:8080/api/v1/pocasi/upd";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(null);

        mvc.perform(put(url).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).param("temp","290.0")).andExpect(status().isBadRequest()).andReturn();
    }
}