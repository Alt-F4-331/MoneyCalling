package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Service.UtilizatorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilizatori")
public class UtilizatorController {
    private final UtilizatorService utilizatorService;


    public UtilizatorController(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }
    @Operation(summary = "Obtine utilizator dupa email")
    @GetMapping("/email")
    public ResponseEntity<Utilizator> getUtilizatorByEmail(@RequestParam String email)
    {
        Optional<Utilizator> utilizator=utilizatorService.getByEmail(email);
        return utilizator.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //permite cautarea unui user dupa emailk
    //foloseste parametrul email di URL(ex:/api/utilizatori/email?email=test@example.com)
    //daca user-ul e gasit,returneaza ResponseEntity.ok(utilizator)(cod de status HTTP E 200)
    //daca nu e gasit,returneaza Https.Status.NOT_FOUND(400)


    @Operation(summary = "Obtine utilizator dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable int id) {
        Optional<Utilizator> utilizator = utilizatorService.getById(id);
        return utilizator.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //la fel ca la getUtilizatorByEmail

    //exemplu URL: /api/utilizatori/1

    @Operation(summary = "Sterge utilizator dupa id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizator(@PathVariable int id) {
        utilizatorService.stergeUtilizatorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //sterge utilizatorul cu un id specific
    //primeste ID-ul utilizatorului din URL: /API/UTILIZATOR/1
    //returneaza statusul HTTP 204(utilizatorul a fost sters)

    @Operation(summary = "Sterge toti utilizatorii")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        utilizatorService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //sterge tot

    @Operation(summary = "Adauga un utilizator")
    @PostMapping
    public ResponseEntity<Utilizator> createUtilizator(@RequestBody Utilizator utilizator) {
        Utilizator savedUtilizator = utilizatorService.saveUtilizator(utilizator);
        return new ResponseEntity<>(savedUtilizator, HttpStatus.CREATED);
    }//Metoda primeste datele utilizatorului in corpul solicitarii sub forma de JSON
     //status HTTP  e 201

    @Operation(summary = "Afiseaza toti utilizatorii")
    @GetMapping
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        List<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        return new ResponseEntity<>(utilizatori, HttpStatus.OK);
    }//returneaza HTTP status 200
}

