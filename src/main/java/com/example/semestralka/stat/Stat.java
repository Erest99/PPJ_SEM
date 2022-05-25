package com.example.semestralka.stat;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Stat{


    @Id
    @Column(name="tag",updatable = false, unique = true,columnDefinition = "varchar (5)")
    private String tag;
    private String name;

    public Stat() {

    }

    public Stat(String tag, String name, String mesta) {
        this.tag = tag;
        this.name = name;
    }
}
