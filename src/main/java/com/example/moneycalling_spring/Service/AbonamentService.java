package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.AbonamentRepository;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AbonamentService {
    private final AbonamentRepository abonamentRepository;
    private final DiagramaRepository diagramaRepository;
    private final UtilizatorRepository utilizatorRepository;
    private final CheltuialaService cheltuialaService;

    // Constructor pentru injectarea dependintelor
    public AbonamentService(AbonamentRepository abonamentRepository, DiagramaRepository diagramaRepository, UtilizatorRepository utilizatorRepository1, CheltuialaService cheltuialaService) {
        this.abonamentRepository = abonamentRepository;
        this.diagramaRepository = diagramaRepository;
        this.utilizatorRepository = utilizatorRepository1;
        this.cheltuialaService = cheltuialaService;
    }

    // Metoda pentru obtinerea tuturor abonamentelor
    public List<Abonament> getAllAbonamente() {
        return abonamentRepository.findAll();
    }

    // Metoda pentru obtinerea unui abonament dupa ID
    public Optional<Abonament> getAbonamentById(int id) {
        return abonamentRepository.findById(id);
    }

    // Metoda pentru obtinerea abonamentelor dupa ID-ul utilizatorului
    public List<Abonament> getAbonamenteByUtilizatorId(int utilizatorId) {
        return abonamentRepository.findByUtilizatorId(utilizatorId);
    }

    // Metoda pentru adaugarea unui nou abonament
    public Abonament addAbonament(Abonament abonament) {
        return abonamentRepository.save(abonament);
    }

    // Metoda pentru stergerea unui abonament dupa ID
    public void deleteAbonament(int id) {
        abonamentRepository.deleteById(id);
    }

    // Metoda pentru obtinerea primului ID disponibil
    public int getFirstAvailableId() {
        List<Integer> allIds = abonamentRepository.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    // Metoda pentru obtinerea unui abonament dupa utilizator si nume
    public Optional<Abonament> getAbonamentByUserAndName(Utilizator utilizator, String nume) {
        return abonamentRepository.findByUtilizatorAndNume(utilizator, nume);
    }

    // Task programat pentru verificarea abonamentelor zilnic la miezul noptii
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(fixedRate = 60000) asta a fost pentru test,apeleaza la fiecare minut
    public void verificaAbonamente() {
        LocalDate today = LocalDate.now();

        // Verifica abonamentele lunare
        List<Abonament> abonamenteLunare = abonamentRepository.findByTipAbonament("Lunar");
        abonamenteLunare.stream()
                .filter(abonament -> abonament.getZi() == today.getDayOfMonth())
                .forEach(this::procesareAbonamentLunar);

        // Verifica abonamentele anuale
        List<Abonament> abonamenteAnuale = abonamentRepository.findByTipAbonament("Anual");
        abonamenteAnuale.stream()
                .filter(abonament -> abonament.getZi() == today.getDayOfMonth() && abonament.getLuna() == today.getMonthValue())
                .forEach(this::procesareAbonamentAnual);
    }

    // Metoda pentru procesarea abonamentelor lunare
    private void procesareAbonamentLunar(Abonament abonament) {
        Utilizator utilizator = abonament.getUtilizator();
        Optional<Diagrama> optionalDiagrama = diagramaRepository.findByUserAndActiva(utilizator);

        if (!optionalDiagrama.isPresent()) {
            System.out.println("Diagrama activa nu a fost gasita pentru utilizator: " + utilizator.getNume());
            return;
        }

        Diagrama diagrama = optionalDiagrama.get();
        if (diagrama.getProcenteCheltuieli() == null || diagrama.getProcenteCheltuieli().isEmpty()) {
            System.out.println("Procentele cheltuielilor lipsesc pentru diagrama: " + diagrama.getId());
            return;
        }

        float sumaAbonament = abonament.getValoare();
        Cheltuiala ch = new Cheltuiala(
                cheltuialaService.getFirstAvailableId(),
                abonament.getNume(),
                sumaAbonament,
                Cheltuiala.TipCheltuiala.DIVERTISMENT,
                diagrama
        );

        Float procentRamas = diagrama.getProcenteCheltuieli().get(Cheltuiala.TipCheltuiala.DIVERTISMENT);
        float venitTotal = utilizator.getProfil().getVenit();

        if (procentRamas == null || (procentRamas * venitTotal) / 100 < sumaAbonament) {
            return;
        }

        float procentNou = procentRamas - (sumaAbonament / venitTotal) * 100;
        diagrama.getProcenteCheltuieli().put(Cheltuiala.TipCheltuiala.DIVERTISMENT, procentNou);
        diagramaRepository.save(diagrama);
        cheltuialaService.saveCheltuiala(ch);

        System.out.println("Abonamentul lunar procesat cu succes: " + abonament.getNume());
    }

    // Metoda pentru procesarea abonamentelor anuale
    private void procesareAbonamentAnual(Abonament abonament) {
        Utilizator utilizator = abonament.getUtilizator();
        Optional<Diagrama> optionalDiagrama = diagramaRepository.findByUserAndActiva(utilizator);

        if (!optionalDiagrama.isPresent()) {
            System.out.println("Diagrama activa nu a fost gasita pentru utilizator: " + utilizator.getNume());
            return;
        }

        Diagrama diagrama = optionalDiagrama.get();
        float sumaAbonament = abonament.getValoare();
        Cheltuiala ch = new Cheltuiala(
                cheltuialaService.getFirstAvailableId(),
                abonament.getNume(),
                sumaAbonament,
                Cheltuiala.TipCheltuiala.CONTAINER,
                diagrama
        );

        ProfilFinanciar profilFinanciar = utilizator.getProfil();
        float containerEconomii = profilFinanciar.getContainerEconomii();

        if (containerEconomii < sumaAbonament) {
            return;
        }

        profilFinanciar.setContainerEconomii(containerEconomii - sumaAbonament);
        utilizator.setProfil(profilFinanciar);
        utilizatorRepository.save(utilizator);
        cheltuialaService.saveCheltuiala(ch);

        System.out.println("Abonamentul anual procesat cu succes: " + abonament.getNume());
    }
}