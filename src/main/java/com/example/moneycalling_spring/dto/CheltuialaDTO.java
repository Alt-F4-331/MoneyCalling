package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CheltuialaDTO {

    @NotBlank(message = "Numele cheltuielii nu poate fi gol.")
    @Size(max = 100, message = "Numele cheltuielii nu poate depăși 100 de caractere.")
    private String nume;

    @Min(value = 0, message = "Suma trebuie să fie un număr pozitiv.")
    private float suma;

    @NotNull(message = "Tipul cheltuielii este obligatoriu.")
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
