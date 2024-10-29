package com.example.moneycalling_spring.Domain;

import jakarta.persistence.*;

/*
 Pentru user am putea sa adaugam inca o clasa numita provioriu "ProfilFinanciar" care sa aiba: venit, dataSalar, containerEconomii
                                                               "User" sa ramana doar cu: nume, prenume, dataNasterii, numarTelefon, domiciliu
 */
@Entity
@Table(name="Utilizator")//Tabela din baza de date
public class Utilizator extends Entitate {
    private String nume;
    private String prenume;
    private String parola;
    private String email;
    @Embedded
    private Data dataNasterii;


    private String sex;

    private String numarTelefon;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profil_id", referencedColumnName = "id")
    private ProfilFinanciar profil;

    public Utilizator(){

    }

    public Utilizator(int id, String nume, String prenume, String parola, String email, Data dataNasterii,String sex, String numarTelefon ,ProfilFinanciar idProfil) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.email = email;
        this.dataNasterii = dataNasterii;
        this.sex=sex;
        this.numarTelefon = numarTelefon;
        this.profil=idProfil;

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

    public ProfilFinanciar getProfil() {
        return profil;
    }

    public void setProfil(ProfilFinanciar profil) {
        this.profil = profil;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }


}
