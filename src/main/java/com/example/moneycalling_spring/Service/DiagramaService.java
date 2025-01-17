package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Data;
import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Exception.ResourceNotFoundException;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiagramaService {
    @Autowired
    private DiagramaRepository diagramarepo;

    @Autowired
    public DiagramaService(DiagramaRepository diagrama) {
        this.diagramarepo = diagrama;
    }

    // Metoda pentru salvarea unei diagrame
    public Diagrama saveDiagrama(Diagrama diagrama) {
        Optional<Diagrama> diagrama1 = diagramarepo.findByDataAndUser(
                diagrama.getDataDiagrama().getLuna(),
                diagrama.getDataDiagrama().getAn(),
                diagrama.getUser().getId()
        );

        // daca diagrama1 este prezenta, arunca exceptie
        return diagramarepo.save(diagrama);
        // adauga sau actualizeaza diagrama cu ID-ul dat
    }

    // Metoda pentru obtinerea primului ID disponibil
    public int getFirstAvailableId() {
        List<Integer> allIds = diagramarepo.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    // Metoda pentru stergerea unei diagrame dupa ID
    public void stergeDiagramaById(int id) {
        diagramarepo.deleteById(id);
        // sterge dupa ID
    }

    // Metoda pentru obtinerea tuturor diagramelor
    public List<Diagrama> getAllDiagrame() {
        return diagramarepo.findAll();
        // returneaza toate diagramele
        // pentru admin
    }

    // Metoda pentru stergerea tuturor diagramelor
    public void deleteAll() {
        diagramarepo.deleteAll();
        // sterge toate diagramele
    }

    // Metoda pentru obtinerea unei diagrame dupa ID
    public Optional<Diagrama> getById(int id) {
        return diagramarepo.findById(id);
    }

    // Metoda pentru obtinerea tuturor diagramelor dupa utilizator
    public List<Diagrama> getAllDiagrameByUtilizator(Utilizator utilizator) {
        return diagramarepo.findByUser(utilizator);
    }

    // Metoda pentru obtinerea diagramei active dupa utilizator
    public Optional<Diagrama> getDiagramaActivaByUtilizator(Utilizator utilizator) {
        return diagramarepo.findByUserAndActiva(utilizator);
    }

    // Metoda pentru setarea unei diagrame ca activa
    public void seteazaDiagramaActiva(Diagrama diagrama) {
        // Obtine toate diagramele utilizatorului
        List<Diagrama> diagrameUtilizator = diagramarepo.findByUser(diagrama.getUser());

        // Marcheaza toate diagramele ca inactive
        for (Diagrama d : diagrameUtilizator) {
            d.setActiva(false);
        }

        // Seteaza diagrama curenta ca activa
        diagrama.setActiva(true);

        // Salveaza toate modificarile
        diagramarepo.saveAll(diagrameUtilizator); // Salveaza diagramele inactivate
        diagramarepo.save(diagrama); // Salveaza diagrama activa
    }

    // Metoda pentru obtinerea unei diagrame dupa data si utilizator
    public Optional<Diagrama> findDiagramaByDataAndUser(int luna, int an, int userId) {
        return diagramarepo.findByDataAndUser(luna, an, userId);
    }

    // Metoda pentru obtinerea ultimelor diagrame ale unui utilizator
    public List<Diagrama> getUltimeleDiagrame(Utilizator utilizator, int numarLuni) {
        // Obtine diagrama activa pentru utilizator
        Optional<Diagrama> diagramaActivaOpt = getDiagramaActivaByUtilizator(utilizator);

        if (diagramaActivaOpt.isEmpty()) {
            throw new RuntimeException("No active diagram found for the specified user.");
        }

        // Determina data activa din diagrama activa
        Diagrama diagramaActiva = diagramaActivaOpt.get();
        Data dataActiva = diagramaActiva.getDataDiagrama();
        LocalDate dataLimita = LocalDate.of(dataActiva.getAn(), dataActiva.getLuna(), 1).minusMonths(numarLuni-1);

        // Obtine toate diagramele utilizatorului
        List<Diagrama> diagrameUtilizator = diagramarepo.findByUser(utilizator);

        // Filtrare diagrame dupa data limitei bazata pe diagrama activa
        return diagrameUtilizator.stream()
                .filter(diagrama -> {
                    Data dataDiagrama = diagrama.getDataDiagrama();
                    LocalDate dataDiagramaLocalDate = LocalDate.of(dataDiagrama.getAn(), dataDiagrama.getLuna(), 1);
                    return dataDiagramaLocalDate.isAfter(dataLimita) || dataDiagramaLocalDate.isEqual(dataLimita);
                })
                .sorted((d1, d2) -> {
                    // Sortare descrescatoare dupa data
                    Data data1 = d1.getDataDiagrama();
                    Data data2 = d2.getDataDiagrama();
                    return LocalDate.of(data2.getAn(), data2.getLuna(), 1)
                            .compareTo(LocalDate.of(data1.getAn(), data1.getLuna(), 1));
                })
                .collect(Collectors.toList());
    }

    // Metoda pentru crearea si configurarea unei diagrame
    public Diagrama createAndConfigureDiagrama(Utilizator utilizator, int zi, int luna, int an) {
        Diagrama diagrama = new Diagrama();

        Data data = new Data(zi, luna, an);
        diagrama.setDataDiagrama(data);

        // Setam alte atribute
        diagrama.setId(getFirstAvailableId());
        diagrama.setUser(utilizator);
        diagrama.setActiva(true);

        // Initializam procentele
        diagrama.initializeProcente(diagrama, utilizator.getProfil().getContainerEconomii());

        // Salvam si setam diagrama activa
        Diagrama savedDiagrama = saveDiagrama(diagrama);
        seteazaDiagramaActiva(diagrama);

        return savedDiagrama;
    }

    // Metoda pentru calcularea banilor economisiti
    public float baniEconomisiti(Diagrama diagrama, float sumaTotala) {
        // Obtinem map-ul cu procente
        Map<Cheltuiala.TipCheltuiala, Float> procenteMap = diagrama.getProcenteCheltuieli();

        // Calculam suma procentelor excluzand CONTAINER
        float sumaProcente = 0.0f;

        for (Map.Entry<Cheltuiala.TipCheltuiala, Float> entry : procenteMap.entrySet()) {
            Cheltuiala.TipCheltuiala tip = entry.getKey();
            Float procent = entry.getValue();

            // Excludem tipul CONTAINER
            if (!tip.equals(Cheltuiala.TipCheltuiala.CONTAINER)) {
                sumaProcente += procent;
            }
        }

        float rezultat = (sumaProcente / 100) * sumaTotala;
        BigDecimal rezultatFinal = BigDecimal.valueOf(rezultat);
        return rezultatFinal.setScale(2, RoundingMode.HALF_UP).floatValue();
        // Calculam valoarea finala (procent cumulativ aplicat la suma totala)
    }
}