package com.example.moneycalling_spring.dto;

public class CheltuialaRequestDTO {

    private int id;
    private String nume;
    private float suma;
    private int idDiagrama; // doar ID-ul diagramei, nu întregul obiect

    public CheltuialaRequestDTO() {
    }

    public CheltuialaRequestDTO(int id, String nume, float suma, int idDiagrama) {
        this.id = id;
        this.nume = nume;
        this.suma = suma;
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
}
