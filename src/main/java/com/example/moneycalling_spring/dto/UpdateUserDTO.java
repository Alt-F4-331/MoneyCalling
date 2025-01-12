package com.example.moneycalling_spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserDTO {


    @NotBlank(message = "Numele nu poate fi gol.")
    @Size(max = 50, message = "Numele nu poate depăși 50 de caractere.")
    private String nume;

    @NotBlank(message = "Prenumele nu poate fi gol.")
    @Size(max = 50, message = "Prenumele nu poate depăși 50 de caractere.")
    private String prenume;

    @NotBlank(message = "Parola nu poate fi goală.")
    @Size(min = 8, message = "Parola trebuie să aibă cel puțin 8 caractere.")
    private String parola;

    @NotBlank(message = "Email-ul nu poate fi gol.")
    @Email(message = "Adresa de email nu este validă.")
    private String email;

    @NotBlank(message = "Numărul de telefon nu poate fi gol.")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Numărul de telefon trebuie să fie valid și să conțină între 10 și 15 cifre.")
    private String numarTelefon;

    public UpdateUserDTO(String nume, String prenume, String parola, String email, String numarTelefon) {
        this.nume = nume;
        this.prenume = prenume;
        this.parola = parola;
        this.email = email;
        this.numarTelefon = numarTelefon;
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

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }
}
