package com.example.semestralka.pocasi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties
@Document
@Data
public class Pocasi {
    @Id
//    @Column(name = "_id", nullable = false)
//    private Long id;
    @Field("city")
    private String name;
    @Field("Time")
    private LocalDateTime time;
    //private List<Main> main;
    @Field("weather")
    private Main main;
    @Field("state")
    private Sys sys;


    public Pocasi(){

    }

    public Pocasi( String name, Main main, Sys sys, LocalDateTime time) {
        this.name = name;
        this.main = main;
        this.sys = sys;
        this.time = time;
    }

}
