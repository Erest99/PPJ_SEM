package com.example.semestralka.pocasi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Date;

//@Data
@Document
@JsonIgnoreProperties
public class Main {
    private Double temp;
    private Integer pressure;


    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Main(Double temp, Integer pressure) {
        this.temp = temp;
        this.pressure = pressure;
    }

    public Main() {
    }
}
