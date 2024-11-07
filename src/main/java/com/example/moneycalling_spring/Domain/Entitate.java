package com.example.moneycalling_spring.Domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;
@MappedSuperclass
public class Entitate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1000L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    public Entitate(){

    }

    public Entitate(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
