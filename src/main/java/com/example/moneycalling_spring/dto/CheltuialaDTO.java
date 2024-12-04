package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Cheltuiala;

public class CheltuialaDTO {

    private String nume;
    private float suma;

    private Cheltuiala.TipCheltuiala tipCheltuiala;

    public CheltuialaDTO(String nume, float suma, Cheltuiala.TipCheltuiala tipCheltuiala) {
        this.nume = nume;
        this.suma = suma;
        this.tipCheltuiala = tipCheltuiala;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }

    public Cheltuiala.TipCheltuiala getTipCheltuiala() {
        return tipCheltuiala;
    }

    public void setTipCheltuiala(Cheltuiala.TipCheltuiala tipCheltuiala) {
        this.tipCheltuiala = tipCheltuiala;
    }
}
