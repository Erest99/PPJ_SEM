package com.example.semestralka.mesto;

import javax.persistence.*;

@Entity  //pro hybernate
@Table
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

    public Mesto() {
    }

    public Mesto(Long id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public Mesto(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Mesto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
