package com.example.semestralka.pocasi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties
@AllArgsConstructor
@Data
public class MyJson {


    private String myid;
    private String name;
    private LocalDateTime time;
    private Main main;
    private Sys sys;


    public MyJson(){

    }

    public MyJson(String name, Main main, Sys sys, LocalDateTime time) {
        this.name = name;
        this.main = main;
        this.sys = sys;
        this.time = time;
    }


}
