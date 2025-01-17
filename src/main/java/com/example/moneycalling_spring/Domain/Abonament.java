package com.example.moneycalling_spring.Domain;

import com.example.moneycalling_spring.Exception.ValidTipAbonament;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
        name = "Abonament",
        uniqueConstraints = @UniqueConstraint(columnNames = {"utilizator_id", "nume"})
)
@ValidTipAbonament // Adnotare personalizată pentru validarea tipului de abonament
public class Abonament extends Entitate{

    @NotBlank(message = "Numele abonamentului nu poate fi gol.")
    @Size(max = 100, message = "Numele abonamentului nu poate avea mai mult de 100 de caractere.")
    private String nume; // Numele abonamentului

    @Positive(message = "Valoarea abonamentului trebuie să fie pozitivă.")
    private float valoare; // Valoarea abonamentului

    @NotBlank(message = "Tipul abonamentului trebuie specificat (lunar sau anual).")
    @Pattern(regexp = "Lunar|Anual", message = "Tipul abonamentului trebuie să fie 'Lunar' sau 'Anual'.")
    private String tipAbonament; // Tipul abonamentului: Lunar sau Anual

    @Min(value = 1, message = "Ziua trebuie să fie cel puțin 1.")
    @Max(value = 28, message = "Ziua nu poate depăși 28.")
    private int zi; // Ziua de facturare (1-28)

    @Min(value = 1, message = "Luna trebuie să fie cel puțin 1.")
    @Max(value = 12, message = "Luna nu poate depăși 12.")
    private Integer luna; // Luna de facturare (1-12), utilizată doar pentru abonamente anuale

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator; // Utilizatorul asociat acestui abonament

    // Constructori
    public Abonament() {
    }

    public Abonament(int id, String nume, float valoare, String tipAbonament, int zi, Integer luna, Utilizator utilizator) {
        super(id);
        this.nume = nume;
        this.valoare = valoare;
        this.tipAbonament = tipAbonament;
        this.zi = zi;
        this.luna = luna;
        this.utilizator = utilizator;
    }

    // Getters și Setters
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

    public int getZi() {
        return zi;
    }

    public void setZi(int zi) {
        this.zi = zi;
    }

    public Integer getLuna() {
        return luna;
    }

    public void setLuna(Integer luna) {
        this.luna = luna;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }
}