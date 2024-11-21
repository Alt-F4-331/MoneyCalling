package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Diagrama;

public class CheltuialaRequestDTO {

    private int id;
    private String nume;
    private float suma;

    private float procent;
    private int idDiagrama; // doar ID-ul diagramei, nu întregul obiect

    public CheltuialaRequestDTO() {
    }

    public CheltuialaRequestDTO(int id, String nume, float suma,float procent, int idDiagrama) {
        this.id = id;
        this.nume = nume;
        this.suma = suma;
        this.procent = procent;
        this.idDiagrama = idDiagrama;
    }

    // Getters și setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdDiagrama() {
        return idDiagrama;
    }

    public void setIdDiagrama(int idDiagrama) {
        this.idDiagrama = idDiagrama;
    }

    // Metodă pentru a mapa din DTO în Entity
    public Cheltuiala mapToEntity(Diagrama diagrama) {
        return new Cheltuiala(
                this.id,
                this.nume,
                this.suma,
                this.procent,
                diagrama // Setăm obiectul Diagrama direct în entitate
        );
    }

    // Metodă statică pentru a crea un DTO din Entity
    public static CheltuialaRequestDTO mapToDTO(Cheltuiala cheltuiala) {
        return new CheltuialaRequestDTO(
                cheltuiala.getId(),
                cheltuiala.getNume(),
                cheltuiala.getSuma(),
                cheltuiala.getProcent(),
                cheltuiala.getDiagrama() != null ? cheltuiala.getDiagrama().getId() : 0
        );
    }
}
