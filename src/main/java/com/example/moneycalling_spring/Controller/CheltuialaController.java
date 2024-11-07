package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Service.CheltuialaService;
import com.example.moneycalling_spring.Service.DiagramaService;
import com.example.moneycalling_spring.dto.CheltuialaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.moneycalling_spring.Domain.Diagrama;

import java.util.Optional;

@RestController
@RequestMapping("/api/cheltuieli")
public class CheltuialaController {
    private final CheltuialaService cheltuialaService;
    private final DiagramaService diagramaService;


    public CheltuialaController(CheltuialaService cheltuialaService, DiagramaService diagramaService) {
        this.cheltuialaService = cheltuialaService;
        this.diagramaService= diagramaService;
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
    public ResponseEntity<Cheltuiala> createCheltuiala(@RequestBody CheltuialaRequestDTO cheltuialaRequestDTO)
    {
        Optional<Diagrama> diagrama_opt = diagramaService.getById(cheltuialaRequestDTO.getId());

        if (diagrama_opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Dacă nu există, returnăm 404
        }

        Cheltuiala ch = new Cheltuiala();

        // Dacă utilizatorul există, îl asociem diagramei
        Diagrama diagrama = diagrama_opt.get();
        // Obținem utilizatorul din Optional

        ch.setId(cheltuialaRequestDTO.getId());
        ch.setDiagrama(diagrama);
        ch.setNume(cheltuialaRequestDTO.getNume());
        ch.setSuma(cheltuialaRequestDTO.getSuma());
        Cheltuiala savedCheltuiala = cheltuialaService.saveCheltuiala(ch);
        return new ResponseEntity<>(savedCheltuiala, HttpStatus.CREATED);
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
