package com.example.moneycalling_spring.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProfilFinanciar")
public class ProfilFinanciar extends Entitate{

    private float venit;

    private String domiciliu;
    private float containerEconomii;
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
