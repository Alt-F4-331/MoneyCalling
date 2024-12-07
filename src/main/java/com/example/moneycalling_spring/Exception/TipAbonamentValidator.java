package com.example.moneycalling_spring.Exception;

import com.example.moneycalling_spring.Domain.Abonament;
import com.example.moneycalling_spring.dto.AbonamentDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class TipAbonamentValidator implements ConstraintValidator<ValidTipAbonament, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof AbonamentDTO) {
            AbonamentDTO abonamentDTO = (AbonamentDTO) obj;
            if ("Anual".equalsIgnoreCase(abonamentDTO.getTipAbonament()) && abonamentDTO.getLuna() == null) {
                context.disableDefaultConstraintViolation(); // Dezactivează mesajul de eroare implicit
                context.buildConstraintViolationWithTemplate("Luna trebuie să fie specificată pentru abonamentele anuale.")
                        .addConstraintViolation(); // Adaugă un mesaj personalizat
                return false;
            }
        } else if (obj instanceof Abonament) {
            Abonament abonament = (Abonament) obj;
            if ("Anual".equalsIgnoreCase(abonament.getTipAbonament()) && abonament.getLuna() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Luna trebuie să fie specificată pentru abonamentele anuale.")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

}

