package com.example.semestralka.mesto;

import lombok.Data;

import javax.persistence.*;

@Entity  //pro hybernate
@Table
@Data
public class Mesto {
    @Id
    @SequenceGenerator(
            name = "mesto_sequence",
            sequenceName = "mesto_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "mesto_sequence"
    )
    private Long id;
    private String name;
    private String state;

    public Mesto(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public Mesto() {

    }
}
