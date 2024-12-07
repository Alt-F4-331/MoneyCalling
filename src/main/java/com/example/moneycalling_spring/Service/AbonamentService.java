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





    public AbonamentService(AbonamentRepository abonamentRepository, UtilizatorService utilizatorService, DiagramaRepository diagramaRepository, UtilizatorRepository utilizatorRepository, UtilizatorRepository utilizatorRepository1, CheltuialaService cheltuialaService) {
        this.abonamentRepository = abonamentRepository;

        this.diagramaRepository = diagramaRepository;

        this.utilizatorRepository = utilizatorRepository1;
        this.cheltuialaService = cheltuialaService;
    }

    public List<Abonament> getAllAbonamente() {
        return abonamentRepository.findAll();
    }

    public Optional<Abonament> getAbonamentById(int id) {
        return abonamentRepository.findById(id);
    }

    public List<Abonament> getAbonamenteByUtilizatorId(int utilizatorId) {
        return abonamentRepository.findByUtilizatorId(utilizatorId);
    }

    public Abonament addAbonament(Abonament abonament) {
        return abonamentRepository.save(abonament);
    }

    public void deleteAbonament(int id) {
        abonamentRepository.deleteById(id);
    }

    public int getFirstAvailableId() {
        List<Integer> allIds = abonamentRepository.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    public Optional<Abonament> getAbonamentByUserAndName(Utilizator utilizator, String nume) {
        return abonamentRepository.findByUtilizatorAndNume(utilizator, nume);
    }

    // Acesta este un task care se va executa la începutul fiecărei zile
    @Scheduled(cron = "0 0 0 * * ?")//Se execută zilnic la miezul nopții
    //@Scheduled(fixedRate = 60000) asta a fost pentru test,apeleaza la fiecare minut
    public void verificaAbonamente() {
        LocalDate today = LocalDate.now();

        // Verifică abonamentele lunare
        List<Abonament> abonamenteLunare = abonamentRepository.findByTipAbonament("Lunar");
        abonamenteLunare.stream()
                .filter(abonament -> abonament.getZi() == today.getDayOfMonth())
                .forEach(this::procesareAbonamentLunar);

        // Verifică abonamentele anuale
        List<Abonament> abonamenteAnuale = abonamentRepository.findByTipAbonament("Anual");
        abonamenteAnuale.stream()
                .filter(abonament -> abonament.getZi() == today.getDayOfMonth() && abonament.getLuna() == today.getMonthValue())
                .forEach(this::procesareAbonamentAnual);
    }

    //@Transactional
    private void procesareAbonamentLunar(Abonament abonament) {
        Utilizator utilizator = abonament.getUtilizator();
        Optional<Diagrama> optionalDiagrama = diagramaRepository.findByUserAndActiva(utilizator);

        if (!optionalDiagrama.isPresent()) {
            System.out.println("Diagrama activă nu a fost găsită pentru utilizator: " + utilizator.getNume());
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

    //@Transactional
    private void procesareAbonamentAnual(Abonament abonament) {
        Utilizator utilizator = abonament.getUtilizator();
        Optional<Diagrama> optionalDiagrama = diagramaRepository.findByUserAndActiva(utilizator);

        if (!optionalDiagrama.isPresent()) {
            System.out.println("Diagrama activă nu a fost găsită pentru utilizator: " + utilizator.getNume());
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
