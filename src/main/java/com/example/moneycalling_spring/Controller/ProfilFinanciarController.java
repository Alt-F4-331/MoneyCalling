package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.ProfilFinanciarService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiluri-financiare")
public class ProfilFinanciarController {

    private final ProfilFinanciarService profilFinanciarService;
    private final UtilizatorService utilizatorService;
    private final JwtUtil jwtUtil;

    @Autowired
    public ProfilFinanciarController(ProfilFinanciarService profilFinanciarService, UtilizatorService utilizatorService, JwtUtil jwtUtil) {
        this.utilizatorService = utilizatorService;
        this.profilFinanciarService = profilFinanciarService;
        this.jwtUtil = jwtUtil;
    }

    // Endpoint pentru a sterge un profil financiar dupa ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Sterge un profil")
    public ResponseEntity<Void> deleteProfilFinanciarById(@PathVariable int id) {
        profilFinanciarService.stergeProfilFinanciarById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru a obtine toate profilurile financiare
    @GetMapping
    @Operation(summary = "Returneaza toate profilurile")
    public ResponseEntity<List<ProfilFinanciar>> getAllProfiluriFinanciare() {
        List<ProfilFinanciar> profiluri = profilFinanciarService.getAllProfiluriFinanciare();
        return new ResponseEntity<>(profiluri, HttpStatus.OK);
    }

    @GetMapping("/token")
    @Operation(summary = "Returneaza profil financiar dupa user logat")
    public ResponseEntity<ProfilFinanciar> getProfilFinanciarByUserLogat(@RequestHeader("Authorization") String token) {
        int userId = jwtUtil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        ProfilFinanciar profil = utilizator.getProfil();

        return new ResponseEntity<>(profil, HttpStatus.OK);
    }

    @GetMapping("/complet")
    @Operation(summary = "Verifica daca profilul financiar al utilizatorului logat este complet")
    public ResponseEntity<Boolean> esteProfilFinanciarComplet(@RequestHeader("Authorization") String token) {
        int userId = jwtUtil.getUserIdByToken(token);

        // Gasim utilizatorul dupa ID
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Utilizator inexistent
        }

        Utilizator utilizator = utilizatorOptional.get();
        ProfilFinanciar profil = utilizator.getProfil();

        // Verificam daca profilul exista
        if (profil == null) {
            return new ResponseEntity<>(false, HttpStatus.OK); // Profil inexistent => incomplet
        }

        // Verificam daca profilul este complet
        boolean esteComplet = profilEsteComplet(profil);
        return new ResponseEntity<>(esteComplet, HttpStatus.OK);
    }

    // Metoda auxiliara pentru validarea completitudinii profilului financiar
    private boolean profilEsteComplet(ProfilFinanciar profil) {
        return profil.getVenit() != 0 && // Venitul trebuie sa fie mai mare decat 0
                profil.getDomiciliu() != null && // Domiciliul nu trebuie sa fie null
                profil.getContainerEconomii() != 0 && // Containerul economiilor nu trebuie sa fie null
                profil.getDataSalar() != 0; // Data salariului nu trebuie sa fie null sau goala
    }

    // Endpoint pentru a sterge toate profilurile financiare
    @DeleteMapping
    @Operation(summary = "Sterge toate profilurile")
    public ResponseEntity<Void> deleteAllProfiluriFinanciare() {
        profilFinanciarService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}