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
    public ProfilFinanciarController(ProfilFinanciarService profilFinanciarService, UtilizatorService utilizatorService, JwtUtil jwtUtil)
    {
        this.utilizatorService = utilizatorService;
        this.profilFinanciarService = profilFinanciarService;
        this.jwtUtil = jwtUtil;
    }


    // Endpoint pentru a șterge un profil financiar după ID
    @DeleteMapping("/{id}")
    @Operation(summary = "sterge un profil")
    public ResponseEntity<Void> deleteProfilFinanciarById(@PathVariable int id) {

        profilFinanciarService.stergeProfilFinanciarById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru a obține toate profilurile financiare
    @GetMapping("/api/profiluri-financiare")
    @Operation(summary = "returneaza toate profilurile")
    public ResponseEntity<List<ProfilFinanciar>> getAllProfiluriFinanciare() {
        List<ProfilFinanciar> profiluri = profilFinanciarService.getAllProfiluriFinanciare();
        return new ResponseEntity<>(profiluri, HttpStatus.OK);
    }

    @GetMapping("/api/profiluri-financiare/token")
    @Operation(summary = "returneaza profil financiar dupa user logat")
    public ResponseEntity<ProfilFinanciar> getProfilFinanciarByUserLogat(@RequestHeader("Authorization") String token)
    {
        int userId = jwtUtil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        ProfilFinanciar profil = utilizator.getProfil();

        return new ResponseEntity<>(profil,HttpStatus.OK);
    }

    // Endpoint pentru a șterge toate profilurile financiare
    @DeleteMapping
    @Operation(summary = "sterge toate profilurile")
    public ResponseEntity<Void> deleteAllProfiluriFinanciare() {
        profilFinanciarService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

