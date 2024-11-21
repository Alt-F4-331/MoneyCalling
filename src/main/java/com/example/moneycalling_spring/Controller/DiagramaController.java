package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Data;
import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Service.DiagramaService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.DiagramaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/diagrame")
public class DiagramaController {

    private final DiagramaService diagramaService;


    private final UtilizatorService utilizatorService;

    public DiagramaController(DiagramaService service,UtilizatorService utilizatorService)
    {
        this.diagramaService = service;
        this.utilizatorService = utilizatorService;
    }

    @Operation(summary = "Obtine diagrama dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Diagrama> getDiagramaById(@PathVariable int id) {
        Optional<Diagrama> diagrama = diagramaService.getById(id);
        return diagrama.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*@Operation(summary = "Obtine diagrama dupa luna si an")
    @GetMapping("/{data}")
    public ResponseEntity<Diagrama> getDiagramaByData(@PathVariable Data data){
        Optional<Diagrama> diagrama = diagramaService.getDiagramaByData(data.getLuna(), data.getAn());
        return diagrama.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
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

        diagrama.setId(diagramaRequestDTO.getId());
        diagrama.setUser(user);
        Diagrama savedDiagrama = diagramaService.saveDiagrama(diagrama);
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

}
