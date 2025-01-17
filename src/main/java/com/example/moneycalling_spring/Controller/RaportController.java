package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.*;
import com.example.moneycalling_spring.dto.RaportRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/rapoarte")
public class RaportController {

    private final RaportService raportService;
    private final UtilizatorService utilizatorService;
    private final DiagramaService diagramaService;
    private final CheltuialaService cheltuialaService;
    private final JwtUtil jwtutil;
    private final AbonamentService abonamentService;

    @Autowired
    public RaportController(RaportService raportService, UtilizatorService utilizatorService, DiagramaService diagramaService, CheltuialaService cheltuialaService, JwtUtil jwtutil, AbonamentService abonamentService) {
        this.raportService = raportService;
        this.utilizatorService = utilizatorService;
        this.diagramaService = diagramaService;
        this.cheltuialaService = cheltuialaService;
        this.jwtutil = jwtutil;
        this.abonamentService = abonamentService;
    }

    // Endpoint pentru salvarea sau actualizarea unui raport
    @PostMapping
    @Operation(summary = "Adauga un nou raport")
    public ResponseEntity<RaportRequestDTO> saveRaport(@RequestBody RaportRequestDTO dto) {
        Diagrama diagrama = diagramaService.getById(dto.getIdDiagrama()).get();  // No exception handling
        Raport raport = RaportRequestDTO.mapToEntity(dto, diagrama);
        Raport savedRaport = raportService.saveRaport(raport);

        RaportRequestDTO responseDto = RaportRequestDTO.mapToDTO(savedRaport);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Endpoint pentru stergerea unui raport dupa ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Sterge un raport")
    public ResponseEntity<Void> deleteRaportById(@PathVariable int id) {
        raportService.stergeRaportById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru obtinerea tuturor rapoartelor dupa ID-ul Diagramei
    @GetMapping("/diagrama/{idDiagrama}")
    @Operation(summary = "Returneaza toate rapoartele dupa diagrama")
    public ResponseEntity<List<RaportRequestDTO>> getAllRapoarteByIdDiagrama(@PathVariable int idDiagrama) {
        Diagrama diagrama = diagramaService.getById(idDiagrama).get();  // No exception handling
        List<Raport> rapoarte = raportService.getAllRapoarteByDiagrama(diagrama);

        List<RaportRequestDTO> responseDtos = rapoarte.stream()
                .map(RaportRequestDTO::mapToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    // Endpoint pentru stergerea tuturor rapoartelor dupa ID-ul Diagramei
    @DeleteMapping("/diagrama/{idDiagrama}")
    @Operation(summary = "Sterge toate rapoartele dupa diagrama")
    public ResponseEntity<Void> deleteAllRapoarteByIdDiagrama(@PathVariable int idDiagrama) {
        Diagrama diagrama = diagramaService.getById(idDiagrama).get();  // No exception handling
        raportService.deleteAllRapoarteByDiagrama(diagrama);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint pentru sugerarea chiriei pe baza venitului
    @GetMapping("/sugereaza-chirie")
    @Operation(summary = "Sugereaza chiria pe baza venitului")
    public ResponseEntity<Float> sugereazaChirie(@RequestHeader("Authorization") String token) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();  // No exception handling
        float venit = utilizator.getProfil().getVenit();

        Diagrama diagrama = diagramaService.getDiagramaActivaByUtilizator(utilizator).get(); // No exception handling

        Float procentLoc = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);
        float rezultat = (procentLoc * venit) / 100;
        rezultat = Math.round(rezultat * 100) / 100.0f;

        float chirieSugerata = raportService.sugereazaChirieByVenit(rezultat, diagrama);
        return new ResponseEntity<>(chirieSugerata, HttpStatus.OK);
    }

    // Endpoint pentru sugerarea ratei pe baza sumei si numarului de ani
    @GetMapping("/sugereaza-rata")
    @Operation(summary = "Sugereaza rata pe luna, dupa suma pentru TRANSPORT")
    public ResponseEntity<Float> sugereazaRata(@RequestHeader("Authorization") String token) {
        float procentRata = 66;
        int userId = jwtutil.getUserIdByToken(token);

        Utilizator utilizator = utilizatorService.getById(userId).get();  // No exception handling
        float venit = utilizator.getProfil().getVenit();

        Diagrama diagrama = diagramaService.getDiagramaActivaByUtilizator(utilizator).get(); // No exception handling

        Float procentTransport = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.TRANSPORT, 0.0f);
        float rezultat = (procentTransport * venit) / 100;
        rezultat = Math.round(rezultat * 100) / 100.0f;
        float rezultatFinal = (procentRata * rezultat) / 100;
        rezultatFinal = Math.round(rezultatFinal * 100) / 100.0f;

        return new ResponseEntity<>(rezultatFinal, HttpStatus.OK);
    }

    @GetMapping("/calculeaza-rata")
    @Operation(summary = "Sugereaza rata pe baza sumei si numarului de ani")
    public ResponseEntity<Float> calculeazaRata(@RequestParam float sumaPropusa, @RequestParam int luni) {
        float rata = raportService.sugereazaRata(sumaPropusa, luni);
        return new ResponseEntity<>(rata, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/adauga-rata")
    @Operation(summary = "Sugereaza rata pe baza sumei si numarului de ani")
    public ResponseEntity<?> addRata(@RequestHeader("Authorization") String token, @RequestParam float rataPropusa) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();  // No exception handling

        Diagrama diagrama = diagramaService.getDiagramaActivaByUtilizator(utilizator).get();

        Float procentMaximtrans = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.TRANSPORT, 0.0f);
        float sumaMaximaLocuinta = (utilizator.getProfil().getVenit() * procentMaximtrans) / 100;

        if(rataPropusa > sumaMaximaLocuinta)
            return new ResponseEntity<>("Proposed installment exceeds the maximum limit allowed for transport! Maximum allowed:  "+sumaMaximaLocuinta,HttpStatus.BAD_REQUEST);


        Cheltuiala ch = new Cheltuiala(cheltuialaService.getFirstAvailableId(), "installment", rataPropusa, Cheltuiala.TipCheltuiala.TRANSPORT, diagrama);
        cheltuialaService.saveCheltuiala(ch);

        Float procentRamas = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.TRANSPORT, 0.0f);
        float procentNou = procentRamas - (rataPropusa / utilizator.getProfil().getVenit()) * 100;

        diagrama.getProcenteCheltuieli().put(Cheltuiala.TipCheltuiala.TRANSPORT, procentNou);
        diagramaService.saveDiagrama(diagrama);
        return new ResponseEntity<>("The installment has been added",HttpStatus.OK);

    }

    @PostMapping("/initiaza-chirie")
    @Operation(summary = "Initiaza procesul pentru adaugarea chiriei")
    public ResponseEntity<String> initiazaChirie(@RequestHeader("Authorization") String token, @RequestParam float chiriePropusa) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();  // No exception handling
        float venit = utilizator.getProfil().getVenit();

        Diagrama diagrama = diagramaService.getDiagramaActivaByUtilizator(utilizator).get();
        Float procentLoc = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);
        float rezultat = (procentLoc * venit) / 100;
        rezultat = Math.round(rezultat * 100) / 100.0f; // No exception handling

        float chirieSugerata = raportService.sugereazaChirieByVenit(rezultat, diagrama);

        // Verificare fara exceptii, doar returneaza mesaj
        Float procentMaximLocuinta = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);
        float sumaMaximaLocuinta = (venit * procentMaximLocuinta) / 100;


        if (chiriePropusa > sumaMaximaLocuinta) {
            return new ResponseEntity<>("Proposed rent exceeds the maximum limit allowed for housing! Maximum allowed: " + sumaMaximaLocuinta, HttpStatus.BAD_REQUEST);
        }

        if (chiriePropusa > chirieSugerata) {
            return new ResponseEntity<>("Proposed rent exceeds the suggested amount of " + chirieSugerata + ". Continue? Call the /confirma-chirie endpoint to confirm or decline.", HttpStatus.OK);
        }

        Cheltuiala ch = new Cheltuiala(cheltuialaService.getFirstAvailableId(), "Rent", chiriePropusa, Cheltuiala.TipCheltuiala.LOCUINTA, diagrama);
        cheltuialaService.saveCheltuiala(ch);
        Float procentRamas = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);

        float procentNou = procentRamas - (chiriePropusa / utilizator.getProfil().getVenit()) * 100;

        diagrama.getProcenteCheltuieli().put(Cheltuiala.TipCheltuiala.LOCUINTA, procentNou);
        diagramaService.saveDiagrama(diagrama);
        return new ResponseEntity<>("Proposed rent has been automatically accepted.", HttpStatus.OK);
    }

    @PostMapping("/confirma-chirie")
    @Operation(summary = "Confirma sau respinge chiria propusa")
    public ResponseEntity<String> confirmaChirie(@RequestHeader("Authorization") String token, @RequestParam float chiriePropusa) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();  // No exception handling

        Diagrama diagrama = diagramaService.getDiagramaActivaByUtilizator(utilizator).get();  // No exception handling

        Cheltuiala ch = new Cheltuiala(cheltuialaService.getFirstAvailableId(), "Rent", chiriePropusa, Cheltuiala.TipCheltuiala.LOCUINTA, diagrama);
        cheltuialaService.saveCheltuiala(ch);
        Float procentRamas = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.LOCUINTA, 0.0f);

        float procentNou = procentRamas - (chiriePropusa / utilizator.getProfil().getVenit()) * 100;

        diagrama.getProcenteCheltuieli().put(Cheltuiala.TipCheltuiala.LOCUINTA, procentNou);
        diagramaService.saveDiagrama(diagrama);
        return new ResponseEntity<>("Proposed rent has been accepted and added.", HttpStatus.OK);
    }

    @GetMapping("/sugereaza-vacanta")
    @Operation(summary = "Sugereaza alocarea bugetului pentru vacanta")
    public ResponseEntity<?> sugereazaBugetVacanta(@RequestHeader("Authorization") String token, @RequestParam int nrZile, @RequestParam float bugetTotal) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();
        float containerEconomii = utilizator.getProfil().getContainerEconomii();

        if (bugetTotal <= 0) {
            return new ResponseEntity<>(Map.of("message", "Budget is negative"), HttpStatus.BAD_REQUEST); // Buget invalid
        }
        if (bugetTotal > containerEconomii) {
            return new ResponseEntity<>(Map.of("message", "Budget must be less than the amount in the savings container"), HttpStatus.BAD_REQUEST); // Buget invalid
        }

        // Distributia procentuala
        float procentTransport = 0.3f; // 30% transport
        float procentCazare = 0.4f;    // 40% cazare
        float procentAltele = 0.3f;    // 30% alte cheltuieli

        // Calculul sumelor
        float sumaTransport = bugetTotal * procentTransport;
        float sumaCazare = bugetTotal * procentCazare / nrZile;
        float sumaAltele = bugetTotal * procentAltele;

        // Rotunjirea valorilor la doua zecimale
        sumaTransport = Math.round(sumaTransport * 100) / 100.0f;
        sumaCazare = Math.round(sumaCazare * 100) / 100.0f;
        sumaAltele = Math.round(sumaAltele * 100) / 100.0f;

        // Crearea unui HashMap pentru rezultate
        Map<String, Float> bugetDistribuit = new HashMap<>();
        bugetDistribuit.put("Transport", sumaTransport);
        bugetDistribuit.put("Cazare", sumaCazare);
        bugetDistribuit.put("Altele", sumaAltele);

        // Stocarea chiriei propuse
        raportService.stocheazaChiriePropusa(userId, bugetTotal);

        // Returneaza un obiect JSON cu datele relevante
        return new ResponseEntity<>(Map.of(
                "bugetDistribuit", bugetDistribuit,
                "message", "Proposed budget is: " + bugetDistribuit + " Do you want to continue? Call the /confirma-vacanta endpoint to confirm or decline."
        ), HttpStatus.OK);
    }

    @PostMapping("/confirma-vacanta")
    @Operation(summary = "Confirma sau respinge sumele pentru vacanta")
    public ResponseEntity<String> confirmaVacanta(@RequestHeader("Authorization") String token, @RequestParam boolean confirm) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();  // No exception handling

        Optional<Float> chiriePropusaOptional = raportService.getChiriePropusa(userId);
        if (chiriePropusaOptional.isEmpty()) {
            return new ResponseEntity<>("No proposed rent pending.", HttpStatus.BAD_REQUEST);
        }
        float bugetPropus = chiriePropusaOptional.get();

        Diagrama diagrama = diagramaService.getDiagramaActivaByUtilizator(utilizator).get();  // No exception handling

        if (confirm) {
            Cheltuiala ch = new Cheltuiala(cheltuialaService.getFirstAvailableId(), "Holiday", bugetPropus, Cheltuiala.TipCheltuiala.CONTAINER, diagrama);
            cheltuialaService.saveCheltuiala(ch);

            ProfilFinanciar profilFinanciar = utilizator.getProfil();
            profilFinanciar.setContainerEconomii(profilFinanciar.getContainerEconomii() - bugetPropus);
            utilizator.setProfil(profilFinanciar);
            utilizatorService.saveUtilizator(utilizator);

            return new ResponseEntity<>("Proposed amount for vacation has been accepted and added.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Proposed amount for vacation has been declined.", HttpStatus.OK);
        }
    }

    @GetMapping("/economii")
    @Operation(summary = "Arata progresul economiilor de la o luna data")
    public ResponseEntity<Map<String, Float>> getProgres(@RequestHeader("Authorization") String token, @RequestParam("luni") int luni) {
        int userId = jwtutil.getUserIdByToken(token);
        Utilizator utilizator = utilizatorService.getById(userId).get();

        // Obtine ultimele diagrame
        List<Diagrama> diagrame = diagramaService.getUltimeleDiagrame(utilizator, luni);

        // Creeaza Map-ul rezultat
        Map<String, Float> rezultat = diagrame.stream()
                .collect(Collectors.toMap(
                        diagrama -> diagrama.getDataDiagrama().getLuna() + " " + diagrama.getDataDiagrama().getAn(), // Cheia: Luna si anul
                        diagrama -> {
                            float procent = diagrama.getProcenteCheltuieli().getOrDefault(Cheltuiala.TipCheltuiala.CONTAINER, 0f);
                            return Math.round(procent * 100) / 100.0f; // Rotunjire la doua zecimale
                        },
                        (v1, v2) -> v1, // In caz de duplicate (nu ar trebui sa fie), ia primul
                        LinkedHashMap::new // Pastreaza ordinea originala
                ));

        return ResponseEntity.ok(rezultat);
    }
}