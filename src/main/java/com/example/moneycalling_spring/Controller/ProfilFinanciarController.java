package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Service.ProfilFinanciarService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiluri-financiare")
public class ProfilFinanciarController {

    private final ProfilFinanciarService profilFinanciarService;
    private final UtilizatorService utilizatorService;

    @Autowired
    public ProfilFinanciarController(ProfilFinanciarService profilFinanciarService, UtilizatorService utilizatorService)
    {
        this.utilizatorService = utilizatorService;
        this.profilFinanciarService = profilFinanciarService;
    }

    // Endpoint pentru a salva sau actualiza un profil financiar
   /* @PostMapping
    @Operation(summary = "adauga un nou profil")
    public ResponseEntity<ProfilFinanciar> saveProfilFinanciar(@RequestBody CreareProfilDTO profilFinanciar) {

        ProfilFinanciar savedProfil = profilFinanciarService.saveProfilFinanciar(profilFinanciar);
        return new ResponseEntity<>(savedProfil, HttpStatus.CREATED);
    }*/

    // Endpoint pentru a șterge un profil financiar după ID
    @DeleteMapping("/{id}")
    @Operation(summary = "sterge un profil")
    public ResponseEntity<Void> deleteProfilFinanciarById(@PathVariable int id) {

        profilFinanciarService.stergeProfilFinanciarById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru a obține toate profilurile financiare
    @GetMapping
    @Operation(summary = "returneaza toate profilurile")
    public ResponseEntity<List<ProfilFinanciar>> getAllProfiluriFinanciare() {
        List<ProfilFinanciar> profiluri = profilFinanciarService.getAllProfiluriFinanciare();
        return new ResponseEntity<>(profiluri, HttpStatus.OK);
    }

    // Endpoint pentru a șterge toate profilurile financiare
    @DeleteMapping
    @Operation(summary = "sterge toate profilurile")
    public ResponseEntity<Void> deleteAllProfiluriFinanciare() {
        profilFinanciarService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

