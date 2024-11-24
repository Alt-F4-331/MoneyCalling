package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.RaportService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.RaportRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rapoarte")
public class RaportController {

    private final RaportService raportService;
    private final UtilizatorService utilizatorService;
    private final DiagramaRepository diagramaRepository;

    private final JwtUtil jwtutil;

    @Autowired
    public RaportController(RaportService raportService, UtilizatorService utilizatorService, DiagramaRepository diagramaRepository, JwtUtil jwtutil) {
        this.raportService = raportService;
        this.utilizatorService = utilizatorService;
        this.diagramaRepository = diagramaRepository;
        this.jwtutil = jwtutil;
    }

    // Endpoint pentru salvarea sau actualizarea unui raport
    @PostMapping
    @Operation(summary = "adauga un nou raport")
    public ResponseEntity<RaportRequestDTO> saveRaport(@RequestBody RaportRequestDTO dto) {
        Diagrama diagrama = diagramaRepository.findById(dto.getIdDiagrama())
                .orElseThrow(() -> new IllegalArgumentException("ID Diagrama invalid: " + dto.getIdDiagrama()));

        Raport raport = RaportRequestDTO.mapToEntity(dto, diagrama);
        Raport savedRaport = raportService.saveRaport(raport);

        RaportRequestDTO responseDto = RaportRequestDTO.mapToDTO(savedRaport);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Endpoint pentru ștergerea unui raport după ID
    @DeleteMapping("/{id}")
    @Operation(summary = "sterge un raport")
    public ResponseEntity<Void> deleteRaportById(@PathVariable int id) {
        raportService.stergeRaportById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru obținerea tuturor rapoartelor după ID-ul Diagramei
    @GetMapping("/diagrama/{idDiagrama}")
    @Operation(summary = "returneaza toate rapoartele dupa diagrama")
    public ResponseEntity<List<RaportRequestDTO>> getAllRapoarteByIdDiagrama(@PathVariable int idDiagrama) {
        Diagrama diagrama = diagramaRepository.findById(idDiagrama)
                .orElseThrow(() -> new IllegalArgumentException("ID Diagrama invalid: " + idDiagrama));

        List<Raport> rapoarte = raportService.getAllRapoarteByDiagrama(diagrama);

        List<RaportRequestDTO> responseDtos = rapoarte.stream()
                .map(RaportRequestDTO::mapToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    // Endpoint pentru ștergerea tuturor rapoartelor după ID-ul Diagramei
    @DeleteMapping("/diagrama/{idDiagrama}")
    @Operation(summary = "sterge toate rapoartele dupa diagrama")
    public ResponseEntity<Void> deleteAllRapoarteByIdDiagrama(@PathVariable int idDiagrama) {
        Diagrama diagrama = diagramaRepository.findById(idDiagrama)
                .orElseThrow(() -> new IllegalArgumentException("ID Diagrama invalid: " + idDiagrama));

        raportService.deleteAllRapoarteByDiagrama(diagrama);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru sugerarea chiriei pe baza venitului
    @GetMapping("/sugereaza-chirie")
    @Operation(summary = "Sugerează chiria pe baza venitului")
    public ResponseEntity<Float> sugereazaChirie(@RequestHeader ("Authorization") String token) {
        int userId = jwtutil.getUserIdByToken(token);
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();
        float venit = utilizator.getProfil().getVenit();
        float chirieSugerata = raportService.sugereazaChirieByVenit(venit);
        return new ResponseEntity<>(chirieSugerata, HttpStatus.OK);
    }

    // Endpoint pentru sugerarea ratei pe baza sumei și numărului de ani
    @GetMapping("/sugerseaza-rata")
    @Operation(summary = "Sugerează rata pe baza sumei și numărului de ani")
    public ResponseEntity<Float> sugereazaRata(
            @RequestParam float suma,
            @RequestParam int ani) {
        float rataSugerata = raportService.sugereazaRataByVenit(suma, ani);
        return new ResponseEntity<>(rataSugerata, HttpStatus.OK);
    }
}
