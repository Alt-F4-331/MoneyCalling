package com.example.moneycalling_spring.Exception;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
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

    // Gestionarea erorilor de validare
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {
        // Log pentru debugging
        ex.getConstraintViolations().forEach(violation -> {
            System.out.println("Field: " + violation.getPropertyPath() + ", Message: " + violation.getMessage());
        });

        // Construiește un mesaj detaliat din toate încălcările de validare
        StringBuilder errorMessage = new StringBuilder("Validare eșuată pentru următoarele câmpuri:\n");
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(String.format("Câmp: '%s', Mesaj: '%s'\n", violation.getPropertyPath(), violation.getMessage()));
        }

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    // Tratează erorile de validare cu MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        // Log pentru a arăta ce erori sunt asociate cu BindingResult
        System.out.println("Intrat în handleValidationException cu erori: " + ex.getBindingResult().toString());

        // Extrage erorile de tip FieldError și ObjectError
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

        // Log pentru a arăta câmpurile eronate și mesajele lor
        fieldErrors.forEach(error -> {
            System.out.println("Field: " + error.getField() + ", Message: " + error.getDefaultMessage());
        });

        // Log pentru erorile globale (validări la nivel de clasă)
        globalErrors.forEach(error -> {
            System.out.println("Global Error: " + error.getObjectName() + ", Message: " + error.getDefaultMessage());
        });

        // Crează un mesaj care combină toate erorile (FieldError + ObjectError)
        String errorMessage = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // Adaugă erorile globale în mesajul de eroare
        String globalErrorMessages = globalErrors.stream()
                .map(error -> error.getObjectName() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        if (!globalErrorMessages.isEmpty()) {
            errorMessage = errorMessage.isEmpty() ? globalErrorMessages : errorMessage + ", " + globalErrorMessages;
        }

        // Verifică dacă errorMessage este gol
        if (errorMessage.isEmpty()) {
            System.out.println("ErrorMessage este gol!");
        } else {
            System.out.println("Mesaj de eroare generat: " + errorMessage);
        }

        // Returnează mesajul de eroare într-un răspuns BAD_REQUEST
        return new ResponseEntity<>("Erori de validare: " + errorMessage, HttpStatus.BAD_REQUEST);
    }




    // Tratează alte excepții legale
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        return new ResponseEntity<>("Eroare: Tipul de date este incorect", HttpStatus.BAD_REQUEST);
    }
    // Alte excepții neprevăzute
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("A apărut o eroare necunoscută.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
