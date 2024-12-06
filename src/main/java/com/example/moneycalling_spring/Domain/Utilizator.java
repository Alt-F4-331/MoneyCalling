package com.example.moneycalling_spring.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

/*
 Pentru user am putea sa adaugam inca o clasa numita provioriu "ProfilFinanciar" care sa aiba: venit, dataSalar, containerEconomii
                                                               "User" sa ramana doar cu: nume, prenume, dataNasterii, numarTelefon, domiciliu
 */
@Entity
@Table(name="Utilizator")//Tabela din baza de date
public class Utilizator extends Entitate {
    @NotBlank(message = "Numele nu poate fi gol.")
    @Size(max = 50, message = "Numele nu poate avea mai mult de 50 de caractere.")
    private String nume;
    @NotBlank(message = "Prenumele nu poate fi gol.")
    @Size(max = 50, message = "Prenumele nu poate avea mai mult de 50 de caractere.")
    private String prenume;

    @NotBlank(message = "Parola nu poate fi goală.")
    @Size(min = 8, message = "Parola trebuie să aibă cel puțin 8 caractere.")
    private String parola;

    @NotBlank(message = "Email-ul nu poate fi gol.")
    @Email(message = "Email-ul trebuie să fie valid.")
    private String email;
    @Embedded
    @Valid
    private Data dataNasterii;

    @NotBlank(message = "Sexul nu poate fi gol.")
    @Pattern(regexp = "Male|Female", message = "Sexul trebuie să fie fie 'Male', fie 'Female'.")
    private String sex;
    @NotBlank(message = "Numărul de telefon nu poate fi gol.")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Numărul de telefon trebuie să fie valid și să conțină între 10 și 15 cifre.")
    private String numarTelefon;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profil_id", referencedColumnName = "id")
    @Valid
    private ProfilFinanciar profil;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Legătura cu Diagrama
    @JsonIgnore
    private List<Diagrama> diagrame;

    public Utilizator(){

    }

    public Utilizator(int id, String nume, String prenume, String parola, String email,Data dataNasterii, String sex, String numarTelefon ,ProfilFinanciar Profil) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.email = email;
        this.dataNasterii = dataNasterii;
        this.sex=sex;
        this.numarTelefon = numarTelefon;
        this.profil=Profil;

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
