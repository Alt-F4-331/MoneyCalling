package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Diagrama;

public class CheltuialaRequestDTO {

    private String nume;
    private float suma;

    private Cheltuiala.TipCheltuiala tipCheltuiala;

    public CheltuialaRequestDTO() {
    }

    public CheltuialaRequestDTO(int id, String nume, float suma,Cheltuiala.TipCheltuiala tip, int idDiagrama) {
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
    public Cheltuiala mapToEntity(int id ,Diagrama diagrama) {
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
