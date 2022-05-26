package com.example.semestralka.stat;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Stat{


    @Id
    @Column(name="tag",updatable = true, unique = true,columnDefinition = "varchar (5)")
    private String tag;
    private String name;

    public Stat() {

    }

    public Stat(String tag, String name) {
        this.tag = tag;
        this.name = name;
    }
}
