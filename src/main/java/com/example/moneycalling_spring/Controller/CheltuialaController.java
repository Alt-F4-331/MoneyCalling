package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.CheltuialaService;
import com.example.moneycalling_spring.Service.DiagramaService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.CheltuialaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.moneycalling_spring.Domain.Diagrama;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cheltuieli")
public class CheltuialaController {
    private final CheltuialaService cheltuialaService;
    private final DiagramaService diagramaService;
    private final UtilizatorService utilizatorService;

    private final JwtUtil jwtUtil;


    public CheltuialaController(CheltuialaService cheltuialaService, DiagramaService diagramaService, UtilizatorService utilizatorService, JwtUtil jwtUtil) {
        this.cheltuialaService = cheltuialaService;
        this.diagramaService= diagramaService;
        this.utilizatorService = utilizatorService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Obtine cheltuiala dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Cheltuiala> getCheltuialaById(@PathVariable int id)
    {
        Optional<Cheltuiala> cheltuiala = cheltuialaService.getById(id);
        return cheltuiala.map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Adauga o noua cheltuiala pentru diagrama")
    @PostMapping
    public ResponseEntity<?> createCheltuiala(@RequestHeader("Authorization") String token,
                                              @RequestBody CheltuialaRequestDTO chDTO) {

        int userId = jwtUtil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();
        Optional<Diagrama> diagrama_opt = diagramaService.getDiagramaActivaByUtilizator(utilizator);

        if (diagrama_opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Dacă nu există, returnăm 404
        }

        Diagrama diagrama = diagrama_opt.get();
        Cheltuiala ch = chDTO.mapToEntity(cheltuialaService.getFirstAvailableId(), diagrama);

        Cheltuiala savedCheltuiala = cheltuialaService.saveCheltuiala(ch);

        Cheltuiala.TipCheltuiala tip = chDTO.getTipCheltuiala();
        float suma = chDTO.getSuma();
        float venitTotal = utilizator.getProfil().getVenit();


        Float procentRamas = diagrama.getProcenteCheltuieli().get(tip);
        if (procentRamas == null || procentRamas * venitTotal < suma) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Nu sunt suficiente procente rămase pentru acest tip de cheltuială.");
        }

        float procentNou= procentRamas - (suma/ venitTotal) *100;//se calculeaza procentul ramas,dupa cheltuiala

        diagrama.getProcenteCheltuieli().put(tip, procentNou);
        System.out.println(procentNou);
        diagramaService.saveDiagrama(diagrama);

        return ResponseEntity.ok("Cheltuiala adaugata cu succes");
    }
    /*
    functia e creare cheltuiala primeste din front id-ul diagramei in care
    se adauga cheltuiala si datele cheltuielii.dupa ce este adaugata cheltuiala,
    din procentul alocat tipului cheltuielii,se scade suma cheltuielii adaugate.
    */



    @Operation(summary = "Sterge cheltuiala dupa id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheltuiala(@PathVariable int id) {
        cheltuialaService.stergeCheltuialaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //sterge cheltuiala cu un id specific
    //primeste ID-ul cheltuielii din URL: /API/cheltuieli/1
    //returneaza statusul HTTP 204(utilizatorul a fost sters)


    // Endpoint pentru a obține toate cheltuielile după id-ul diagramei
    @Operation(summary = "Obtine toate cheltuielile dintr-o diagrama")
    @GetMapping("/diagrama/{idDiagrama}")
    public ResponseEntity<List<CheltuialaRequestDTO>> getAllCheltuieliByIdDiagrama(@PathVariable int idDiagrama) {
        //aceasta functie se foloseste pentru legenda de cheltuileli pentru o diagrama
        Optional<Diagrama> diagrama_opt = diagramaService.getById(idDiagrama);
        if (diagrama_opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Dacă nu există, returnăm 404
        }

        Diagrama diagrama = diagrama_opt.get();

        List<Cheltuiala> cheltuieli = cheltuialaService.getAllCheltuieliByIdDiagrama(diagrama);
        if (cheltuieli.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Transformăm entitățile în DTO-uri
        List<CheltuialaRequestDTO> cheltuialaDTOs = cheltuieli.stream()
                .map(cheltuiala -> new CheltuialaRequestDTO(
                        cheltuiala.getId(),
                        cheltuiala.getNume(),
                        cheltuiala.getSuma(),
                        cheltuiala.getTipCheltuiala(),
                        diagrama.getId() // sau cheltuiala.getIdDiagrama() dacă există direct
                ))
                .toList();

        return ResponseEntity.ok(cheltuialaDTOs);
    }
}
