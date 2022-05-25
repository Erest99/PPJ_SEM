package com.example.semestralka.pocasi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@JsonIgnoreProperties
public class Sys {

    private String country;

    public Sys(String country) {
        this.country = country;
    }

    public Sys() {
    }
}
