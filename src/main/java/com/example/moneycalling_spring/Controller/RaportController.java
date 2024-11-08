package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import com.example.moneycalling_spring.Service.RaportService;
import com.example.moneycalling_spring.dto.RaportRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rapoarte")
public class RaportController {

    private final RaportService raportService;
    private final DiagramaRepository diagramaRepository;

    @Autowired
    public RaportController(RaportService raportService, DiagramaRepository diagramaRepository) {
        this.raportService = raportService;
        this.diagramaRepository = diagramaRepository;
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

        List<Raport> rapoarte = raportService.getAllRapoarteByIdDiagrama(diagrama);

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

        raportService.deleteAllRapoarteByIdDIagrama(diagrama);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
