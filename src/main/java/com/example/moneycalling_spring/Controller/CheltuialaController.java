package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Service.CheltuialaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cheltuieli")
public class CheltuialaController {
    private final CheltuialaService cheltuialaService;


    public CheltuialaController(CheltuialaService cheltuialaService) {
        this.cheltuialaService = cheltuialaService;
    }

    /*@Operation(summary = "Obtine cheltuiala dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<>*/
}
