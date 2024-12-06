package com.example.moneycalling_spring.dto;

import com.example.moneycalling_spring.Domain.Data;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DataDTO {

    @NotBlank(message = "Ziua nu poate fi goală.")
    @Pattern(regexp = "^[0-9]{1,2}$", message = "Ziua trebuie să fie un număr între 1 și 31.")
    @Min(value = 1, message = "Ziua trebuie să fie cel puțin 1.")
    @Max(value = 31, message = "Ziua nu poate depăși 31.")
    private String zi;

    @NotBlank(message = "Luna nu poate fi goală.")
    @Pattern(regexp = "^[0-9]{1,2}$", message = "Luna trebuie să fie un număr între 1 și 12.")
    @Min(value = 1, message = "Luna trebuie să fie cel puțin 1.")
    @Max(value = 12, message = "Luna nu poate depăși 12.")
    private String luna;

    @NotBlank(message = "Anul nu poate fi gol.")
    @Pattern(regexp = "^[0-9]{4}$", message = "Anul trebuie să fie un număr valid de 4 cifre.")
    private String an;

    public DataDTO(String zi, String luna, String an) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
    }

    public DataDTO() {}

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
