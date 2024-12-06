package com.example.moneycalling_spring.Exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidDateValidator.class) // Asociază validatorul
@Target({ElementType.TYPE}) // Aplicabil pe clase
@Retention(RetentionPolicy.RUNTIME) // Disponibil la runtime
public @interface ValidDate {
    String message() default "Data este invalidă."; // Mesaj implicit

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
