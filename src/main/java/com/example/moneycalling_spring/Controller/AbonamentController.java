package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Abonament;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.AbonamentService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.AbonamentDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/abonamente")
public class AbonamentController {
    private final AbonamentService abonamentService;

    private final JwtUtil jwtUtil;

    private final UtilizatorService utilizatorService;

    public AbonamentController(AbonamentService abonamentService, JwtUtil jwtUtil, UtilizatorService utilizatorService) {
        this.abonamentService = abonamentService;
        this.jwtUtil = jwtUtil;
        this.utilizatorService = utilizatorService;
    }

    @GetMapping("/{userId}/{numeAbonament}")
    @Operation(summary = "Aceasta functie se foloseste atunci cand selectezi un abonament pentru a-l sterge")
    public ResponseEntity<Abonament> getAbonamentByUserAndName(
            @RequestHeader("Authorization") String token,
            @PathVariable String numeAbonament) {

        int userId = jwtUtil.getUserIdByToken(token);
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();


        // Căutăm abonamentul pe baza utilizatorului și a numelui
        Optional<Abonament> abonament = abonamentService.getAbonamentByUserAndName(utilizator, numeAbonament);

        return abonament.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


    }



    @GetMapping("/utilizator/{utilizatorId}")
    @Operation(summary = "Afiseaza lista abonamentelor pe care le are un utilizator")
    public List<Abonament> getAbonamenteByUtilizator(@RequestHeader("Authorization") String token) {

        int userId = jwtUtil.getUserIdByToken(token);
        return abonamentService.getAbonamenteByUtilizatorId(userId);
    }

    @PostMapping
    @Operation(summary = "Adauga un abonament pentru un utilizator")
    public ResponseEntity<?> addAbonament(@RequestHeader("Authorization") String token, @Valid @RequestBody AbonamentDTO abonamentDTO) {
        int userId = jwtUtil.getUserIdByToken(token);
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        int idAbonament = abonamentService.getFirstAvailableId();

        Abonament abonament = new Abonament(idAbonament,abonamentDTO.getNume() , abonamentDTO.getValoare(),
                abonamentDTO.getTipAbonament(),abonamentDTO.getZiua(),abonamentDTO.getLuna(),utilizator);

        abonamentService.addAbonament(abonament);


        return ResponseEntity.ok("Abonament adaugat cu succes");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Front-ul trimite id-ul utilizatorului obtinut din endpoint-ul de getByUserAndName")
    public ResponseEntity<Void> deleteAbonament(@PathVariable int id) {
        if (abonamentService.getAbonamentById(id).isPresent()) {
            abonamentService.deleteAbonament(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }
}
