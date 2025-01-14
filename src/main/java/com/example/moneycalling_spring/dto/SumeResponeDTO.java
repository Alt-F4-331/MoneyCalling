package com.example.moneycalling_spring.dto;

public class SumeResponeDTO {

    private float sumaCh;
    private float sumaContainer;
    private float economii;

    public SumeResponeDTO(float sumaCh, float sumaContainer, float economii) {
        this.sumaCh = sumaCh;
        this.sumaContainer = sumaContainer;
        this.economii = economii;
    }

    public float getSumaCh() {
        return sumaCh;
    }

    public void setSumaCh(float sumaCh) {
        this.sumaCh = sumaCh;
    }

    public float getSumaContainer() {
        return sumaContainer;
    }

    public void setSumaContainer(float sumaContainer) {
        this.sumaContainer = sumaContainer;
    }

    public float getEconomii() {
        return economii;
    }

    public void setEconomii(float economii) {
        this.economii = economii;
    }
}
