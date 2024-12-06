package com.example.moneycalling_spring.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "ProfilFinanciar")
public class ProfilFinanciar extends Entitate {

    @Min(value = 0, message = "Venitul trebuie să fie un număr pozitiv.")
    private float venit;

    @NotBlank(message = "Domiciliul nu poate fi gol.")
    private String domiciliu;

    @Min(value = 0, message = "Containerul de economii trebuie să fie un număr pozitiv.")
    private float containerEconomii;

    @Min(value = 1, message = "Data salarului trebuie sa fie o zi.")
    @Max(value = 28, message = "Data salarului trebuie sa fie o zi.")
    private int dataSalar;

    public ProfilFinanciar(int id, float venit, String domiciliu, float containerEconomii, int dataSalar) {
        super(id);
        this.venit = venit;
        this.domiciliu = domiciliu;
        this.containerEconomii = containerEconomii;
        this.dataSalar = dataSalar;
    }

    public ProfilFinanciar() {
        super();
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
