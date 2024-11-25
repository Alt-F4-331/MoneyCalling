package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Data;
import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.CheltuialaService;
import com.example.moneycalling_spring.Service.DiagramaService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.DiagramaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/diagrame")
public class DiagramaController {

    private final DiagramaService diagramaService;


    private final UtilizatorService utilizatorService;

    private final CheltuialaService cheltuialaService;

    private final JwtUtil jwtutil;

    public DiagramaController(DiagramaService service, UtilizatorService utilizatorService, CheltuialaService cheltuialaService, JwtUtil jwtutil)
    {
        this.diagramaService = service;
        this.utilizatorService = utilizatorService;
        this.cheltuialaService = cheltuialaService;
        this.jwtutil = jwtutil;
    }

    @Operation(summary = "Obtine diagrama dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Diagrama> getDiagramaById(@PathVariable int id) {
        Optional<Diagrama> diagrama = diagramaService.getById(id);
        return diagrama.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Obtine toate diagramele")
    @GetMapping
    public ResponseEntity<List<Diagrama>> getAllDiagrame() {
        List<Diagrama> diagrame = diagramaService.getAllDiagrame();
        if (diagrame.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Dacă nu sunt diagrame
        }
        return new ResponseEntity<>(diagrame, HttpStatus.OK);
    }
    //aici ceva nu merge

    @Operation(summary = "Creeaza o noua diagrama")
    @PostMapping
    public ResponseEntity<Diagrama> createDiagrama(@RequestBody DiagramaRequestDTO diagramaRequestDTO) {
        Optional<Utilizator> optionalUser = utilizatorService.getById(diagramaRequestDTO.getUserId()) ;
        // Verificăm dacă utilizatorul există
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Dacă nu există, returnăm 404
        }

        Diagrama diagrama = new Diagrama();

        // Dacă utilizatorul există, îl asociem diagramei
        Utilizator user = optionalUser.get();
        // Obținem utilizatorul din Optional
        Data data = new Data(diagramaRequestDTO.getData().getZi(),diagramaRequestDTO.getData().getLuna(),diagramaRequestDTO.getData().getAn());
        diagrama.setId(diagramaRequestDTO.getId());
        diagrama.setDataDiagrama(data);
        diagrama.setUser(user);
        Diagrama savedDiagrama = diagramaService.saveDiagrama(diagrama);
        float venit = user.getProfil().getVenit();
       // cheltuialaService.adaugaCheltuieli(diagrama);

        return new ResponseEntity<>(savedDiagrama, HttpStatus.CREATED); // Răspuns cu 201 când e salvat
    }

    // Endpoint pentru a șterge o diagrama după ID
    @Operation(summary = "Sterge o diagrama dupa un id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDiagrama(@PathVariable("id") int id) {
        try {
            diagramaService.stergeDiagramaById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Răspuns 204 dacă s-a șters cu succes
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // În caz de eroare
        }
    }

    // Endpoint pentru a șterge toate diagramele
    @Operation(summary = "Sterge toate diagramele")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllDiagrame() {
        try {
            diagramaService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Răspuns 204 pentru ștergerea tuturor
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // În caz de eroare
        }
    }

    // Endpoint pentru obținerea tuturor diagramelor asociate unui utilizator
    @Operation(summary = "Obține toate diagramele pentru un utilizator")
    @GetMapping("/utilizator/{userId}")
    public ResponseEntity<List<Diagrama>> getAllDiagrameByUtilizator(@PathVariable int userId) {
        // Căutăm utilizatorul după ID
        Optional<Utilizator> optionalUtilizator = utilizatorService.getById(userId);

        if (optionalUtilizator.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Returnăm 404 dacă utilizatorul nu există
        }

        // Dacă utilizatorul există, obținem diagramele asociate
        Utilizator utilizator = optionalUtilizator.get();
        List<Diagrama> diagrame = diagramaService.getAllDiagrameByUtilizator(utilizator);

        if (diagrame.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returnăm 204 dacă nu sunt diagrame
        }

        return new ResponseEntity<>(diagrame, HttpStatus.OK); // Returnăm diagramele cu status 200
    }

    @GetMapping("/configurare")
    public ResponseEntity<Map<Cheltuiala.TipCheltuiala, Float>> configurareDiagrama(@RequestHeader("Authorization") String token)
    {
        //functie care returneaza pentru fiecare tip,sumele respective.

        int userId = jwtutil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        float venitTotal = utilizator.getProfil().getVenit();

        Map<Cheltuiala.TipCheltuiala, Float> sumePeTip = new HashMap<>();

        for (Cheltuiala.TipCheltuiala tip : Cheltuiala.TipCheltuiala.values()) {
            float suma = venitTotal * (tip.getProcent() / 100); // Calculăm suma pentru fiecare tip
            sumePeTip.put(tip, suma);
        }

        return new ResponseEntity<>(sumePeTip, HttpStatus.OK);
    }



}
