package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Exception.ValidTipAbonament;
import jakarta.validation.constraints.*;

@ValidTipAbonament
public class AbonamentDTO {
    @NotBlank(message = "Numele abonamentului nu poate fi gol.")
    @Size(max = 100, message = "Numele abonamentului nu poate avea mai mult de 100 de caractere.")
    private String nume;

    @Positive(message = "Valoarea abonamentului trebuie să fie un număr pozitiv.")
    private float valoare;

    @NotBlank(message = "Tipul abonamentului nu poate fi gol.")
    @Pattern(regexp = "Lunar|Anual", message = "Tipul abonamentului trebuie să fie fie 'Lunar', fie 'Anual'.")
    private String tipAbonament;


    @Min(value = 1, message = "Ziua trebuie să fie cel puțin 1.")
    @Max(value = 28, message = "Ziua nu poate depăși 28.")
    private int ziua;

    @Min(value = 1, message = "Luna trebuie să fie cel puțin 1.")
    @Max(value = 12, message = "Luna nu poate depăși 12.")
    private Integer luna; // Optional pentru abonamentele anuale

    public AbonamentDTO() {
    }

    public AbonamentDTO(String nume, float valoare, String tipAbonament, int ziua, Integer luna) {
        this.nume = nume;
        this.valoare = valoare;
        this.tipAbonament = tipAbonament;
        this.ziua = ziua;
        this.luna = luna;
    }

    // Getteri și setteri
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public String getTipAbonament() {
        return tipAbonament;
    }

    public void setTipAbonament(String tipAbonament) {
        this.tipAbonament = tipAbonament;
    }

    public int getZiua() {
        return ziua;
    }

    public void setZiua(int ziua) {
        this.ziua = ziua;
    }

    public Integer getLuna() {
        return luna;
    }

    public void setLuna(Integer luna) {
        this.luna = luna;
    }
}
