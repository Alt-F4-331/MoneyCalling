package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Diagrama;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CheltuialaRequestDTO {

    @NotBlank(message = "Numele cheltuielii nu poate fi gol.")
    @Size(max = 100, message = "Numele cheltuielii nu poate depăși 100 de caractere.")
    private String nume;

    @Min(value = 0, message = "Suma trebuie să fie un număr pozitiv.")
    private float suma;

    @NotNull(message = "Tipul cheltuielii este obligatoriu.")
    private Cheltuiala.TipCheltuiala tipCheltuiala;

    public CheltuialaRequestDTO() {
    }

    public CheltuialaRequestDTO(int id,String nume, float suma, Cheltuiala.TipCheltuiala tip, int idDiagrama) {
        this.nume = nume;
        this.suma = suma;
        this.tipCheltuiala = tip;
    }

    // Getters și setters

    public Cheltuiala.TipCheltuiala getTipCheltuiala() {
        return tipCheltuiala;
    }

    public void setTipCheltuiala(Cheltuiala.TipCheltuiala tipCheltuiala) {
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

    // Metodă pentru a mapa din DTO în Entity
    public Cheltuiala mapToEntity(int id, Diagrama diagrama) {
        return new Cheltuiala(
                id,
                this.nume,
                this.suma,
                this.tipCheltuiala,
                diagrama // Setăm obiectul Diagrama direct în entitate
        );
    }

    // Metodă statică pentru a crea un DTO din Entity
    public static CheltuialaRequestDTO mapToDTO(Cheltuiala cheltuiala) {
        return new CheltuialaRequestDTO(
                cheltuiala.getId(),
                cheltuiala.getNume(),
                cheltuiala.getSuma(),
                cheltuiala.getTipCheltuiala(),
                cheltuiala.getDiagrama() != null ? cheltuiala.getDiagrama().getId() : 0
        );
    }
}
