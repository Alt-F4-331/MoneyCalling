package com.example.moneycalling_spring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
    //În controller-ul CheltuialaController, am folosit InsufficientFundsException pentru a arunca o excepție
    //atunci când suma adăugată la cheltuiala unui tip depășește procentul disponibil din diagrama activă a utilizatorului.

}