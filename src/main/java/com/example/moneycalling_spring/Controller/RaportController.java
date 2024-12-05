package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.CheltuialaService;
import com.example.moneycalling_spring.Service.DiagramaService;
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
    private final DiagramaService diagramaService;

    private final CheltuialaService cheltuialaService;

    private final JwtUtil jwtutil;

    @Autowired
    public RaportController(RaportService raportService, UtilizatorService utilizatorService, DiagramaService diagramaService, CheltuialaService cheltuialaService, JwtUtil jwtutil) {
        this.raportService = raportService;
        this.utilizatorService = utilizatorService;

        this.diagramaService = diagramaService;
        this.cheltuialaService = cheltuialaService;
        this.jwtutil = jwtutil;
    }

    // Endpoint pentru salvarea sau actualizarea unui raport
    @PostMapping
    @Operation(summary = "adauga un nou raport")
    public ResponseEntity<RaportRequestDTO> saveRaport(@RequestBody RaportRequestDTO dto) {
        Diagrama diagrama = diagramaService.getById(dto.getIdDiagrama())
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
        Diagrama diagrama = diagramaService.getById(idDiagrama)
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
        Diagrama diagrama = diagramaService.getById(idDiagrama)
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



        Optional<Diagrama> diagramaOptional = diagramaService.getDiagramaActivaByUtilizator(utilizator);
        if (diagramaOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Diagrama diagrama = diagramaOptional.get();

        float chirieSugerata = raportService.sugereazaChirieByVenit(venit,diagrama);
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


    @PostMapping("/initiaza-chirie")
    @Operation(summary = "Inițiază procesul pentru adăugarea chiriei")
    public ResponseEntity<String> initiazaChirie(
            @RequestHeader("Authorization") String token,
            @RequestBody float chiriePropusa) {

        // Extrage ID-ul utilizatorului din token
        int userId = jwtutil.getUserIdByToken(token);
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>("Utilizatorul nu a fost găsit.", HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();
        float venit = utilizator.getProfil().getVenit();

        // Obține diagrama activă
        Optional<Diagrama> diagramaOptional = diagramaService.getDiagramaActivaByUtilizator(utilizator);
        if (diagramaOptional.isEmpty()) {
            return new ResponseEntity<>("Diagrama activă nu a fost găsită.", HttpStatus.NOT_FOUND);
        }
        Diagrama diagrama = diagramaOptional.get();

        // Obține suma sugerată pentru chirie
        float chirieSugerata = raportService.sugereazaChirieByVenit(venit, diagrama);

        // Obține procentul maxim permis pentru locuință
        Float procentMaximLocuinta = diagrama.getProcenteCheltuieli()
                .getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);
        float sumaMaximaLocuinta = (venit * procentMaximLocuinta) / 100;

        if (chiriePropusa > sumaMaximaLocuinta) {
            return new ResponseEntity<>(
                    "Chiria propusă depășește limita maximă permisă pentru locuință! "
                            + "Maxim permis: " + sumaMaximaLocuinta,
                    HttpStatus.BAD_REQUEST);
        }

        if (chiriePropusa > chirieSugerata) {
            // Stocăm chiria propusă pentru utilizator în sesiune (poți folosi un cache sau o mapare simplă în memorie)
            raportService.stocheazaChiriePropusa(userId, chiriePropusa);
            return new ResponseEntity<>(
                    "Chiria propusă depășește suma sugerată de " + chirieSugerata
                            + ". Continuați? Apelați endpoint-ul /confirma-chirie pentru a confirma sau refuza.",
                    HttpStatus.OK);
        }

        // Salvează chiria
        Cheltuiala ch = new Cheltuiala(cheltuialaService.getFirstAvailableId(),
                "chirie", chiriePropusa, Cheltuiala.TipCheltuiala.LOCUINTA, diagrama);
        cheltuialaService.saveCheltuiala(ch);
        Float procentRamas = diagrama.getProcenteCheltuieli()
                .getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);


        float procentNou= procentRamas - (chiriePropusa/ utilizator.getProfil().getVenit()) *100;//se calculeaza procentul ramas,dupa cheltuiala

        diagrama.getProcenteCheltuieli().put(Cheltuiala.TipCheltuiala.LOCUINTA, procentNou);
        diagramaService.saveDiagrama(diagrama);
        return new ResponseEntity<>("Chiria propusă este acceptată automat.", HttpStatus.OK);
    }
    @PostMapping("/confirma-chirie")
    @Operation(summary = "Confirmă sau respinge chiria propusă")
    public ResponseEntity<String> confirmaChirie(
            @RequestHeader("Authorization") String token,
            @RequestParam boolean confirm) {

        // Extrage ID-ul utilizatorului din token
        int userId = jwtutil.getUserIdByToken(token);
        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>("Utilizatorul nu a fost găsit.", HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        // Recuperăm chiria propusă din context (stocată anterior)
        Optional<Float> chiriePropusaOptional = raportService.getChiriePropusa(userId);
        if (chiriePropusaOptional.isEmpty()) {
            return new ResponseEntity<>("Nu există o chirie propusă în așteptare.", HttpStatus.BAD_REQUEST);
        }
        float chiriePropusa = chiriePropusaOptional.get();

        // Obține diagrama activă
        Optional<Diagrama> diagramaOptional = diagramaService.getDiagramaActivaByUtilizator(utilizator);
        if (diagramaOptional.isEmpty()) {
            return new ResponseEntity<>("Diagrama activă nu a fost găsită.", HttpStatus.NOT_FOUND);
        }
        Diagrama diagrama = diagramaOptional.get();

        if (confirm) {
            // Salvează chiria dacă utilizatorul confirmă
            Cheltuiala ch = new Cheltuiala(cheltuialaService.getFirstAvailableId(),
                    "chirie", chiriePropusa, Cheltuiala.TipCheltuiala.LOCUINTA, diagrama);
            cheltuialaService.saveCheltuiala(ch);
            Float procentRamas = diagrama.getProcenteCheltuieli()
                    .getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);


            float procentNou= procentRamas - (chiriePropusa/ utilizator.getProfil().getVenit()) *100;//se calculeaza procentul ramas,dupa cheltuiala

            diagrama.getProcenteCheltuieli().put(Cheltuiala.TipCheltuiala.LOCUINTA, procentNou);
            diagramaService.saveDiagrama(diagrama);

            // Ștergem contextul chiriei propuse
            raportService.eliminaChiriePropusa(userId);
            return new ResponseEntity<>("Chiria propusă a fost acceptată și salvată.", HttpStatus.OK);
        } else {
            // Dacă utilizatorul refuză, ștergem contextul chiriei propuse
            raportService.eliminaChiriePropusa(userId);
            return new ResponseEntity<>("Chiria propusă a fost refuzată.", HttpStatus.OK);
        }
    }
}
