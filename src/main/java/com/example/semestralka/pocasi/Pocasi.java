package com.example.semestralka.pocasi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties
@Document
@Data
@AllArgsConstructor
public class Pocasi {
//    @Transient
//    public static final String SEQUENCE_NAME = "pocasi_sequence";

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue
    @Field("_id")
    private String myid;
    @Indexed
    private String name;
    @Field("Time")
    private LocalDateTime time;
    @Field("Temperature")
    private Double temp;
    @Field("Preassure")
    private Integer pres;
    @Field("state")
    private String state;


    public Pocasi(){

    }

    public Pocasi(String name, LocalDateTime time, Double temp, Integer pres, String state) {
        this.name = name;
        this.time = time;
        this.temp = temp;
        this.pres = pres;
        this.state = state;
    }
}
