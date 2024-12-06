package com.example.moneycalling_spring.Exception;

import com.example.moneycalling_spring.Domain.Data;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, Data> {

    @Override
    public boolean isValid(Data data, ConstraintValidatorContext context) {
        if (data == null) {
            return true; // O entitate null nu este validată aici
        }

        int zi = data.getZi();
        int luna = data.getLuna();
        int an = data.getAn();

        if (luna < 1 || luna > 12) {
            return false; // Luna invalidă
        }

        int zileInLuna;
        switch (luna) {
            case 4: case 6: case 9: case 11: // Lunile cu 30 de zile
                zileInLuna = 30;
                break;
            case 2: // Februarie
                boolean isLeapYear = (an % 4 == 0 && an % 100 != 0) || (an % 400 == 0);
                zileInLuna = isLeapYear ? 29 : 28;
                break;
            default: // Lunile cu 31 de zile
                zileInLuna = 31;
        }

        return zi >= 1 && zi <= zileInLuna; // Validează ziua în funcție de lună
    }
}

