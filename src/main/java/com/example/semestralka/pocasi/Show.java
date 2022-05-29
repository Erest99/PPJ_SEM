package com.example.semestralka.pocasi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Show {

    private String name;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp time;
    private Double temp;
    private Integer pres;

    public Show(String name, Timestamp time, Double temp, Integer pres, String state) {
        this.name = name;
        this.time = time;
        this.temp = temp - 273.15;
        this.pres = pres;
        this.state = state;
    }

    private String state;

}
