package com.example.moneycalling_spring.Domain;

import com.example.moneycalling_spring.Exception.ValidDate;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;

@Embeddable
@ValidDate
public class Data {

    @Min(value = 1, message = "Ziua trebuie să fie între 1 și 31.")
    @Max(value = 31, message = "Ziua trebuie să fie între 1 și 31.")
    private int zi;

    @Min(value = 1, message = "Luna trebuie să fie între 1 și 12.")
    @Max(value = 12, message = "Luna trebuie să fie între 1 și 12.")
    private int luna;

    @Positive(message = "Anul trebuie să fie un număr pozitiv.")
    @NotNull(message = "Anul nu poate fi nul.")
    private int an;

    public Data() {
    }

    public Data(int zi, int luna, int an) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
       // validateDate(); // Se asigură că datele sunt valide la instanțiere
    }

    @Override
    public String toString() {
        return zi + "." + luna + "." + an;
    }

    public int getZi() {
        return this.zi;
    }

    public int getLuna() {
        return this.luna;
    }

    public int getAn() {
        return this.an;
    }

    public void setZi(int zi) {
        this.zi = zi;

    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public void setAn(int an) {
        this.an = an;
    }

    /**
     * Valideaza dacă ziua, luna si anul formeaza o data valida
     */
    public void validateDate() {
        /*if (luna < 1 || luna > 12) {
            throw new IllegalArgumentException("Luna nu poate fi mai mare decât 12 și trebuie să fie între 1 și 12.");
        }*/

        if (luna == 2) {
            boolean isLeapYear = (an % 4 == 0 && an % 100 != 0) || (an % 400 == 0);
            int maxDaysInFeb = isLeapYear ? 29 : 28;
            if (zi > maxDaysInFeb) {
                throw new IllegalArgumentException("Februarie are maxim " + maxDaysInFeb + " zile în anul " + an);
            }
        } else if (luna == 4 || luna == 6 || luna == 9 || luna == 11) {
            if (zi > 30) {
                throw new IllegalArgumentException("Luna " + luna + " are maxim 30 de zile.");
            }
        } else if (zi > 31) {
            throw new IllegalArgumentException("Luna " + luna + " are maxim 31 de zile.");
        }
    }
}
