package com.example.moneycalling_spring.Exception;

public class ProfilNotFoundException extends RuntimeException {
    public ProfilNotFoundException(String message) {
        super(message);
    }
    //Aceasta va fi aruncată atunci când un profil financiar nu este găsit pentru un utilizator.
}