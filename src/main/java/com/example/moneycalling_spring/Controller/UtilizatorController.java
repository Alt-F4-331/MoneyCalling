package com.example.moneycalling_spring.Controller;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Service.ProfilFinanciarService;
import com.example.moneycalling_spring.Service.UtilizatorService;
import com.example.moneycalling_spring.dto.CreareContDto;
import com.example.moneycalling_spring.dto.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilizatori")
public class UtilizatorController {
    private final UtilizatorService utilizatorService;

    private final ProfilFinanciarService profilFinanciarService;


    public UtilizatorController(UtilizatorService utilizatorService, ProfilFinanciarService profilFinanciarService) {

        this.utilizatorService = utilizatorService;
        this.profilFinanciarService=profilFinanciarService;
    }
    @Operation(summary = "Obtine utilizator dupa email")
    @GetMapping("/email")
    public ResponseEntity<Utilizator> getUtilizatorByEmail(@RequestParam String email)
    {
        Optional<Utilizator> utilizator=utilizatorService.getByEmail(email);
        return utilizator.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //permite cautarea unui user dupa emailk
    //foloseste parametrul email di URL(ex:/api/utilizatori/email?email=test@example.com)
    //daca user-ul e gasit,returneaza ResponseEntity.ok(utilizator)(cod de status HTTP E 200)
    //daca nu e gasit,returneaza Https.Status.NOT_FOUND(400)


    @Operation(summary = "Obtine utilizator dupa id")
    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable int id) {
        Optional<Utilizator> utilizator = utilizatorService.getById(id);
        return utilizator.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //la fel ca la getUtilizatorByEmail

    //exemplu URL: /api/utilizatori/1

    @Operation(summary = "Sterge utilizator dupa id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizator(@PathVariable int id) {
        utilizatorService.stergeUtilizatorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //sterge utilizatorul cu un id specific
    //primeste ID-ul utilizatorului din URL: /API/UTILIZATOR/1
    //returneaza statusul HTTP 204(utilizatorul a fost sters)

    @Operation(summary = "Sterge toti utilizatorii")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        utilizatorService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //sterge tot

    @Operation(summary = "Adauga un utilizator")
    @PostMapping
    public ResponseEntity<Utilizator> createUtilizator(@RequestBody Utilizator utilizator) {
        Utilizator savedUtilizator = utilizatorService.saveUtilizator(utilizator);
        return new ResponseEntity<>(savedUtilizator, HttpStatus.CREATED);
    }//Metoda primeste datele utilizatorului in corpul solicitarii sub forma de JSON
     //status HTTP  e 201

    @Operation(summary = "Afiseaza toti utilizatorii")
    @GetMapping
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        List<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        return new ResponseEntity<>(utilizatori, HttpStatus.OK);
    }//returneaza HTTP status 200


    @Operation(summary = "Autentificare utilizator", description = "Verifică email-ul și parola utilizatorului pentru autentificare")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {


        String email = loginRequest.getEmail();
        String parola = loginRequest.getParola();

        Optional<Utilizator> utilizator = utilizatorService.getByEmail(email);

        if (utilizator.isPresent() && utilizator.get().getParola().equals(parola)) {
            return ResponseEntity.ok("Autentificare reușită");
        } else {
            return new ResponseEntity<>("Email sau parolă incorectă", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Creează un cont de utilizator fără profil financiar completat")
    @PostMapping("/createAccount")
    public ResponseEntity<Utilizator> createAccount(@RequestBody CreareContDto cont) {

        System.out.println("Creare cont primit: ");  // Log pentru a verifica dacă cererea ajunge
        // Inițializează un utilizator gol
        Utilizator utilizator = new Utilizator();

        utilizator.setId(utilizatorService.getFirstAvailableId());
        utilizator.setNume(cont.getNume());
        utilizator.setPrenume(cont.getPrenume());
        utilizator.setParola(cont.getParola());
        utilizator.setEmail(cont.getEmail());
        utilizator.setDataNasterii(cont.getDataNasterii());
        utilizator.setSex(cont.getSex());
        utilizator.setNumarTelefon(cont.getNumarTelefon());

        ProfilFinanciar profilFinanciarGol = new ProfilFinanciar();
        profilFinanciarGol.setId(profilFinanciarService.getFirstAvailableId());

        // Setează profilul financiar gol pentru utilizatorul nou
        utilizator.setProfil(profilFinanciarGol);

        // Salvează utilizatorul cu profilul financiar necompletat
        Utilizator utilizatorSalvat = utilizatorService.saveUtilizator(utilizator);

        // Returnează utilizatorul creat cu HTTP Status 201 Created
        return new ResponseEntity<>(utilizatorSalvat, HttpStatus.CREATED);
    }


}

