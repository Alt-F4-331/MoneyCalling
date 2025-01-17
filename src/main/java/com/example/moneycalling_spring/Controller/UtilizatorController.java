package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.CheltuialaService;
import com.example.moneycalling_spring.Service.DiagramaService;
import com.example.moneycalling_spring.Service.ProfilFinanciarService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.CreareContDto;
import com.example.moneycalling_spring.dto.LoginRequestDTO;
import com.example.moneycalling_spring.dto.ProfilFinanciarDto;
import com.example.moneycalling_spring.dto.UpdateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilizatori")
public class UtilizatorController {
    private final UtilizatorService utilizatorService;
    private final ProfilFinanciarService profilFinanciarService;
    private final JwtUtil jwtutil;
    private final DiagramaService diagramaService;

    public UtilizatorController(UtilizatorService utilizatorService, ProfilFinanciarService profilFinanciarService, JwtUtil jwt, CheltuialaService cheltuialaService, DiagramaService diagramaService) {
        this.utilizatorService = utilizatorService;
        this.profilFinanciarService = profilFinanciarService;
        this.jwtutil = jwt;
        this.diagramaService = diagramaService;
    }

    @Operation(summary = "Obtine utilizator dupa email")
    @GetMapping("/email")
    public ResponseEntity<Utilizator> getUtilizatorByEmail(@RequestParam String email) {
        Optional<Utilizator> utilizator = utilizatorService.getByEmail(email);
        return utilizator.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // permite cautarea unui user dupa email
    // foloseste parametrul email din URL (ex: /api/utilizatori/email?email=test@example.com)
    // daca user-ul e gasit, returneaza ResponseEntity.ok(utilizator) (cod de status HTTP 200)
    // daca nu e gasit, returneaza HttpStatus.NOT_FOUND (404)

    @Operation(summary = "Obtine utilizator dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable int id) {
        Optional<Utilizator> utilizator = utilizatorService.getById(id);
        return utilizator.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // la fel ca la getUtilizatorByEmail
    // exemplu URL: /api/utilizatori/1

    @Operation(summary = "Sterge utilizator dupa id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizator(@PathVariable int id) {
        utilizatorService.stergeUtilizatorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // sterge utilizatorul cu un id specific
    // primeste ID-ul utilizatorului din URL: /api/utilizatori/1
    // returneaza statusul HTTP 204 (utilizatorul a fost sters)

    @Operation(summary = "Sterge toti utilizatorii")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        utilizatorService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // sterge tot

    @Operation(summary = "Adauga un utilizator")
    @PostMapping
    public ResponseEntity<Utilizator> createUtilizator(@Valid @RequestBody Utilizator utilizator) {
        Utilizator savedUtilizator = utilizatorService.saveUtilizator(utilizator);
        return new ResponseEntity<>(savedUtilizator, HttpStatus.CREATED);
    }
    // Metoda primeste datele utilizatorului in corpul solicitarii sub forma de JSON
    // status HTTP e 201

    @Operation(summary = "Afiseaza toti utilizatorii")
    @GetMapping
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        List<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        return new ResponseEntity<>(utilizatori, HttpStatus.OK);
    }
    // returneaza HTTP status 200

    @Operation(summary = "Autentificare utilizator", description = "Verifica email-ul si parola utilizatorului pentru autentificare")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        String parola = loginRequest.getParola();

        Optional<Utilizator> utilizator = utilizatorService.getByEmail(email);

        if (utilizator.isPresent() && utilizator.get().getParola().equals(parola)) {
            int userId = utilizator.get().getId();
            String token = jwtutil.generateToken(userId);

            // Creaza un obiect Map cu token-ul
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", token);

            // Serializare Map intr-un String JSON
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(responseMap);
                return ResponseEntity.ok(jsonResponse);  // Returneaza JSON-ul ca String
            } catch (Exception e) {
                return new ResponseEntity<>("Error serializing response", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Creeaza un cont de utilizator fara profil financiar completat")
    @PostMapping("/createAccount")
    public ResponseEntity<Utilizator> createAccount(@Valid @RequestBody CreareContDto cont) {
        // Initializeaza un utilizator gol
        Utilizator utilizator = new Utilizator();

        utilizator.setId(utilizatorService.getFirstAvailableId());
        utilizator.setNume(cont.getNume());
        utilizator.setPrenume(cont.getPrenume());
        utilizator.setParola(cont.getParola());
        utilizator.setEmail(cont.getEmail());
        utilizator.setDataNasterii(cont.getDataNasterii());
        utilizator.setSex(cont.getSex());
        utilizator.setNumarTelefon(cont.getNumarTelefon());

        ProfilFinanciar profilFinanciarGol = new ProfilFinanciar(profilFinanciarService.getFirstAvailableId());

        // Seteaza profilul financiar gol pentru utilizatorul nou
        utilizator.setProfil(profilFinanciarGol);

        // Salveaza utilizatorul cu profilul financiar necompletat
        Utilizator utilizatorSalvat = utilizatorService.saveUtilizator(utilizator);

        // Returneaza utilizatorul creat cu HTTP Status 201 Created
        return new ResponseEntity<>(utilizatorSalvat, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizeaza datele user-ului logat")
    @PostMapping("/updateAccount")
    public ResponseEntity<Utilizator> updateAccount(@RequestHeader("Authorization") String token, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        int userId = jwtutil.getUserIdByToken(token);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        utilizator.setNume(updateUserDTO.getNume());
        utilizator.setNumarTelefon(updateUserDTO.getNumarTelefon());
        utilizator.setPrenume(updateUserDTO.getPrenume());
        utilizator.setEmail(updateUserDTO.getEmail());
        utilizator.setParola(updateUserDTO.getParola());

        // Salveaza utilizatorul cu profilul financiar necompletat
        Utilizator utilizatorSalvat = utilizatorService.saveUtilizator(utilizator);

        // Returneaza utilizatorul creat cu HTTP Status 201 Created
        return new ResponseEntity<>(utilizatorSalvat, HttpStatus.CREATED);
    }

    @PutMapping("/profil-financiar")
    @Operation(summary = "Actualizeaza profil financiar al utilizatorului logat")
    public ResponseEntity<ProfilFinanciar> updateProfilFinanciar(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ProfilFinanciarDto profilFinanciarNou) {
        // Verifica daca token-ul este valid
        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String jwtToken = token.substring(7);  // Extrage token-ul fara "Bearer "
        if (!jwtutil.validateToken(jwtToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        int userId = jwtutil.extractUserId(jwtToken);

        Optional<Utilizator> utilizatorOptional = utilizatorService.getById(userId);
        if (utilizatorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Utilizator utilizator = utilizatorOptional.get();

        ProfilFinanciar profilExistent = utilizator.getProfil();
        if (profilExistent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Dacă nu există profil, returnează 404
        }

        profilExistent.setVenit(profilFinanciarNou.getVenit());
        profilExistent.setDomiciliu(profilFinanciarNou.getDomiciliu());
        profilExistent.setContainerEconomii(profilFinanciarNou.getContainerEconomii());
        profilExistent.setDataSalar(profilFinanciarNou.getDataSalar());

        ProfilFinanciar profilSalvat = profilFinanciarService.saveProfilFinanciar(profilExistent);
        int zi = profilSalvat.getDataSalar();
        LocalDate today = LocalDate.now();
        int luna = today.getMonthValue();
        int an = today.getYear();

        Optional<Diagrama> optionalDiagrama = diagramaService.findDiagramaByDataAndUser(luna, an, userId);

        if (optionalDiagrama.isEmpty())
            diagramaService.createAndConfigureDiagrama(utilizator, zi, luna, an);

        // Returneaza profilul actualizat
        return ResponseEntity.ok(profilSalvat);
    }
}