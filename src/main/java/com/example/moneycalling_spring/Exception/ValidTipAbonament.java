package com.example.moneycalling_spring.Exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TipAbonamentValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTipAbonament {
    String message() default "Dacă tipul abonamentului este 'Anual', trebuie să specifici și luna.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

