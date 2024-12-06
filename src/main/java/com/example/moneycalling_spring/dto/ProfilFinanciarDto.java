package com.example.moneycalling_spring.dto;

import jakarta.validation.constraints.*;

public class ProfilFinanciarDto {

    // Venit nu poate fi mai mic de 0, trebuie să fie un număr pozitiv
    @Positive(message = "Venitul trebuie să fie un număr pozitiv.")
    private float venit;

    // Domiciliu nu poate fi gol
    @NotBlank(message = "Domiciliul nu poate fi gol.")
    private String domiciliu;

    // Container Economii nu poate fi mai mic de 0, trebuie să fie un număr pozitiv
    @Positive(message = "Containerul de economii trebuie să fie un număr pozitiv.")
    private float containerEconomii;

    // Data salariului trebuie să fie un număr pozitiv
    @Min(value = 1, message = "Data salariului trebuie să fie un număr valid (minim 1).")
    @Max(value = 28 , message = "Data salarilului sa fie maxim 28")
    private int dataSalar;

    public ProfilFinanciarDto(float venit, String domiciliu, float containerEconomii, int dataSalar) {
        this.venit = venit;
        this.domiciliu = domiciliu;
        this.containerEconomii = containerEconomii;
        this.dataSalar = dataSalar;
    }

    public float getVenit() {
        return venit;
    }

    public void setVenit(float venit) {
        this.venit = venit;
    }

    public String getDomiciliu() {
        return domiciliu;
    }

    public void setDomiciliu(String domiciliu) {
        this.domiciliu = domiciliu;
    }

    public float getContainerEconomii() {
        return containerEconomii;
    }

    public void setContainerEconomii(float containerEconomii) {
        this.containerEconomii = containerEconomii;
    }

    public int getDataSalar() {
        return dataSalar;
    }

    public void setDataSalar(int dataSalar) {
        this.dataSalar = dataSalar;
    }
}
