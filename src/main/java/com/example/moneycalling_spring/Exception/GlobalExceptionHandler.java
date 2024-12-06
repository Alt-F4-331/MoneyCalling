package com.example.moneycalling_spring.Exception;

import com.example.moneycalling_spring.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Excepție pentru cazul în care nu se găsește un obiect
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Excepție pentru cazurile în care procentele de cheltuieli nu sunt suficiente
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Excepție pentru validarea entităților

    @ExceptionHandler(ProfilNotFoundException.class)
    public ResponseEntity<String> handleProfilNotFoundException(ProfilNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeleteProfilException.class)
    public ResponseEntity<String> handleDeleteProfilException(DeleteProfilException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidToken(InvalidTokenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UtilizatorNotFoundException.class)
    public ResponseEntity<String> handleUtilizatorNotFound(UtilizatorNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<String> handleInvalidDateException(InvalidDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SaveUtilizatorException.class)
    public ResponseEntity<String> handleSaveUtilizatorException(SaveUtilizatorException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(jakarta.validation.ConstraintViolationException ex) {
        // Log pentru debugging
        ex.getConstraintViolations().forEach(violation -> {
            System.out.println("Field: " + violation.getPropertyPath() + ", Message: " + violation.getMessage());
        });

        // Construiește un mesaj detaliat din toate încălcările de validare
        StringBuilder errorMessage = new StringBuilder("Validare eșuată pentru următoarele câmpuri:\n");

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            // Adaugă un mesaj detaliat pentru fiecare câmp
            errorMessage.append(String.format("Câmp: '%s', Mesaj: '%s'\n", violation.getPropertyPath(), violation.getMessage()));
        }

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>("Erori de validare: " + errorMessage, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        // Aici poți prinde erorile legate de validarea datei
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    // Alte excepții neprevăzute
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("A apărut o eroare necunoscută.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
