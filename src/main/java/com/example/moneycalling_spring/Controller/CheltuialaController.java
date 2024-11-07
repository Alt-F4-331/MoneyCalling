package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Service.CheltuialaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cheltuieli")
public class CheltuialaController {
    private final CheltuialaService cheltuialaService;


    public CheltuialaController(CheltuialaService cheltuialaService) {
        this.cheltuialaService = cheltuialaService;
    }

    @Operation(summary = "Obtine cheltuiala dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Cheltuiala> getCheltuialaById(@PathVariable int id)
    {
        Optional<Cheltuiala> cheltuiala = cheltuialaService.getById(id);
        return cheltuiala.map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Adauga o noua cheltuiala")
    @PostMapping
    public ResponseEntity<Cheltuiala> createCheltuiala(@RequestBody Cheltuiala cheltuiala)
    {
        Cheltuiala cheltuiala1 = cheltuialaService.saveCheltuiala(cheltuiala);
        return new ResponseEntity<>(cheltuiala1,HttpStatus.CREATED);
    }

    @Operation(summary = "Sterge cheltuiala dupa id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheltuiala(@PathVariable int id) {
        cheltuialaService.stergeCheltuialaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //sterge cheltuiala cu un id specific
    //primeste ID-ul cheltuielii din URL: /API/cheltuieli/1
    //returneaza statusul HTTP 204(utilizatorul a fost sters)
}
