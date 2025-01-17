package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.CheltuialaService;
import com.example.moneycalling_spring.Service.DiagramaService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.DiagramaRequestDTO;
import com.example.moneycalling_spring.dto.SumeResponeDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/diagrame")
public class DiagramaController {

    private final DiagramaService diagramaService;
    private final UtilizatorService utilizatorService;
    private final CheltuialaService cheltuialaService;
    private final JwtUtil jwtutil;

    public DiagramaController(DiagramaService service, UtilizatorService utilizatorService, CheltuialaService cheltuialaService, JwtUtil jwtutil) {
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

    @Operation(summary = "Creeaza o noua diagrama")
    @PostMapping
    public ResponseEntity<Diagrama> createDiagrama(@RequestHeader("Authorization") String token,
                                                   @RequestBody @Valid DiagramaRequestDTO diagramaRequestDTO) {
        int userId = jwtutil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        Diagrama diagrama = new Diagrama();

        // Dacă utilizatorul există, îl asociem diagramei
        Data data = new Data(diagramaRequestDTO.getData().getZi(), diagramaRequestDTO.getData().getLuna(), diagramaRequestDTO.getData().getAn());
        diagrama.setId(diagramaRequestDTO.getId());
        diagrama.setDataDiagrama(data);
        diagrama.setUser(utilizator);
        diagrama.setActiva(true);
        diagrama.initializeProcente(diagrama, utilizator.getProfil().getContainerEconomii());
        Diagrama savedDiagrama = diagramaService.saveDiagrama(diagrama);
        diagramaService.seteazaDiagramaActiva(diagrama);

        // atunci cand se creeaza o diagrama noua, diagrama creata devine activa, restul nu sunt active
        return new ResponseEntity<>(savedDiagrama, HttpStatus.CREATED); // Raspuns cu 201 cand e salvat
    }

    // Endpoint pentru a sterge o diagrama dupa ID
    @Operation(summary = "Sterge o diagrama dupa un id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDiagrama(@PathVariable("id") int id) {
        try {
            diagramaService.stergeDiagramaById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Raspuns 204 daca s-a sters cu succes
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // In caz de eroare
        }
    }

    // Endpoint pentru a sterge toate diagramele
    @Operation(summary = "Sterge toate diagramele")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllDiagrame() {
        try {
            diagramaService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Raspuns 204 pentru stergerea tuturor
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // In caz de eroare
        }
    }

    // Endpoint pentru obtinerea tuturor diagramelor asociate unui utilizator
    @Operation(summary = "Obtine toate diagramele pentru un utilizator")
    @GetMapping("/utilizator/{userId}")
    public ResponseEntity<List<Diagrama>> getAllDiagrameByUtilizator(@RequestHeader("Authorization") String token) {
        int userId = jwtutil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();
        List<Diagrama> diagrame = diagramaService.getAllDiagrameByUtilizator(utilizator);

        if (diagrame.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returnam 204 daca nu sunt diagrame
        }

        return new ResponseEntity<>(diagrame, HttpStatus.OK); // Returnam diagramele cu status 200
    }

    @GetMapping("/configurare")
    public ResponseEntity<Map<Cheltuiala.TipCheltuiala, Float>> configurareDiagrama(@RequestHeader("Authorization") String token) {
        // functie care returneaza pentru fiecare tip, sumele respective

        int userId = jwtutil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        float venitTotal = utilizator.getProfil().getVenit();

        Map<Cheltuiala.TipCheltuiala, Float> sumePeTip = new HashMap<>();

        Map<Cheltuiala.TipCheltuiala, Float> procentePeTip = diagramaService.getDiagramaActivaByUtilizator(utilizator).get().getProcenteCheltuieli();

        for (Map.Entry<Cheltuiala.TipCheltuiala, Float> entry : procentePeTip.entrySet()) {
            Cheltuiala.TipCheltuiala tip = entry.getKey();
            float procent = entry.getValue();

            // Calculam suma pentru fiecare tip de cheltuiala
            float suma = venitTotal * (procent / 100);

            // Adaugam suma in map-ul sumePeTip
            sumePeTip.put(tip, suma);
        }

        return new ResponseEntity<>(sumePeTip, HttpStatus.OK);
    }

    @GetMapping("/getByData")
    public ResponseEntity<SumeResponeDTO> getSumeByDataAndUser(
            @RequestParam int luna,
            @RequestParam int an,
            @RequestHeader("Authorization") String token) {

        // Obtine userId din token
        int userId = jwtutil.getUserIdByToken(token);
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);

        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Utilizator utilizator = utilizatorOptional.get();
        Optional<Diagrama> diagramaOptional = diagramaService.findDiagramaByDataAndUser(luna, an, userId);

        if (diagramaOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Diagrama diagrama = diagramaOptional.get();
        List<Cheltuiala> cheltuieli = cheltuialaService.getAllCheltuieliByIdDiagrama(diagrama);

        float sumaCh = 0;
        float sumaContainer = 0;

        // Calculeaza sumele
        for (Cheltuiala cheltuiala : cheltuieli) {
            if (!cheltuiala.getTipCheltuiala().equals(Cheltuiala.TipCheltuiala.CONTAINER)) {
                sumaCh += cheltuiala.getSuma();
            } else {
                sumaContainer += cheltuiala.getSuma();
            }
        }

        // Rotunjire la 2 zecimale
        sumaCh = Math.round(sumaCh * 100) / 100f;
        sumaContainer = Math.round(sumaContainer * 100) / 100f;
        float economii = Math.round((utilizator.getProfil().getVenit() - sumaCh) * 100) / 100f;

        // Construiește DTO-ul pentru raspuns
        SumeResponeDTO responseDTO = new SumeResponeDTO(sumaCh, sumaContainer, economii);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Creeaza o noua diagrama")
    @PostMapping("/finalizare")
    public ResponseEntity<?> finalizareDiagrama(
            @RequestHeader("Authorization") String token) {
        // Buton pentru finalizare diagrama, o noua diagrama se face, iar ce a ramas din vechea diagrama se adauga la containerul de economii

        int userId = jwtutil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        ProfilFinanciar pf = utilizator.getProfil();

        Diagrama diagramaCurenta = diagramaService.getDiagramaActivaByUtilizator(utilizator).get();

        pf.setContainerEconomii(pf.getContainerEconomii() + diagramaService.baniEconomisiti(diagramaCurenta, pf.getVenit()));
        // Actualizeaza containerul, adaugand sumele ramase

        utilizator.setProfil(pf);
        utilizatorService.saveUtilizator(utilizator);
        int luna, an;
        if (diagramaCurenta.getDataDiagrama().getLuna() == 12) {
            luna = 1;
            an = diagramaCurenta.getDataDiagrama().getAn() + 1;
        } else {
            luna = diagramaCurenta.getDataDiagrama().getLuna() + 1;
            an = diagramaCurenta.getDataDiagrama().getAn();
        }

        Diagrama diagramaNoua = diagramaService.createAndConfigureDiagrama(utilizator, pf.getDataSalar(), luna, an);
        return ResponseEntity.ok(diagramaNoua);
    }
}