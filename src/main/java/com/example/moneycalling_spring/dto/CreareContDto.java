package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Data;

public class CreareContDto {

    private String nume;
    private String prenume;
    private String parola;
    private String email;

    private Data dataNasterii;
    private String sex;
    private String numarTelefon;

    public CreareContDto(String nume, String prenume, String parola, String email, @Valid Data dataNasterii, String sex, String numarTelefon) {
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.email = email;
        this.dataNasterii = dataNasterii;
        this.sex = sex;
        this.numarTelefon = numarTelefon;
    }

    public CreareContDto()
    {

    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Data getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Data dataNasterii) {
        this.dataNasterii = dataNasterii;
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }
}
