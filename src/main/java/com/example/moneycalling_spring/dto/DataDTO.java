package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Data;

public class DataDTO {
    private String zi;
    private String luna;
    private String an;

    public DataDTO(String zi, String luna, String an) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
    }

    public DataDTO(){}

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public String getLuna() {
        return luna;
    }

    public void setLuna(String luna) {
        this.luna = luna;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }



    // Metoda care convertește un DataDTO la Data
    public Data toData() {
        return new Data(
                Integer.parseInt(zi),
                Integer.parseInt(luna),
                Integer.parseInt(an)
        );
    }
}
