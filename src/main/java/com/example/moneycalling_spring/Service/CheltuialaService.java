package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Repository.CheltuialaRepository;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import com.example.moneycalling_spring.dto.CheltuialaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.moneycalling_spring.Domain.Diagrama;
import java.util.List;
import java.util.Optional;

@Service
public class CheltuialaService {

    @Autowired
    private CheltuialaRepository cheltuialaRepository;

    @Autowired
    private final DiagramaRepository diagramaRepository;

    @Autowired
    public CheltuialaService(CheltuialaRepository cheltuiala, DiagramaRepository diagramaRepository) {

        this.cheltuialaRepository = cheltuiala;
        this.diagramaRepository = diagramaRepository;
    }


    // Mapping from DTO to Entity
    public Cheltuiala mapToEntity(CheltuialaRequestDTO dto) {
        Cheltuiala cheltuiala = new Cheltuiala();
        cheltuiala.setId(dto.getId());
        cheltuiala.setNume(dto.getNume());
        cheltuiala.setSuma(dto.getSuma());

        // Fetch and set Diagrama
        Diagrama diagrama = diagramaRepository.findById(dto.getIdDiagrama())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Diagrama ID: " + dto.getIdDiagrama()));
        cheltuiala.setDiagrama(diagrama);

        return cheltuiala;
    }

    // Mapping from Entity to DTO
    public CheltuialaRequestDTO mapToDTO(Cheltuiala cheltuiala) {
        CheltuialaRequestDTO dto = new CheltuialaRequestDTO();
        dto.setId(cheltuiala.getId());
        dto.setNume(cheltuiala.getNume());
        dto.setSuma(cheltuiala.getSuma());
        if (cheltuiala.getDiagrama() != null) {
            dto.setIdDiagrama(cheltuiala.getDiagrama().getId());
        }
        return dto;
    }


    //Metoda care adauga o noua cheltuiala
    public Cheltuiala saveCheltuiala(Cheltuiala cheltuiala) {
        return cheltuialaRepository.save(cheltuiala);
        //aceasta metoda poate fi folosita si pentru update:
        //-daca este creata o noua cheltuiala cu un id nou il creeaza
        //-daca exista deja acel id,il actualizeaza
    }

    //Metoda care returneaza toate cheltuielile
    public List<Cheltuiala> getAllCheltuieli() {
        return cheltuialaRepository.findAll();
        //pt admin?
    }

    public int getFirstAvailableId() {
        List<Integer> allIds = cheltuialaRepository.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    public Optional<Cheltuiala> getById(int id) {
        return cheltuialaRepository.findById(id);
    }

    public void stergeCheltuialaById(int id) {
        cheltuialaRepository.deleteById(id);
    }

    // Metodă pentru a șterge toate cheltuielile
    public void deleteAll() {
        cheltuialaRepository.deleteAll();
    }

    public List<Cheltuiala> getAllCheltuieliByIdDiagrama(Diagrama diagrama) {
        return cheltuialaRepository.findByDiagrama(diagrama);
    }

    public void adaugaCheltuieli(Diagrama diagrama) {

        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "locuinta", 0,30, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "alimentatie", 0,15, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "transport", 0,15, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "sanatate", 0,10, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "imbracaminte-incaltaminte", 0,5, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "divertisment", 0,10, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "educatie", 0,5, diagrama));
        saveCheltuiala(new Cheltuiala(getFirstAvailableId(), "economii", 0,10, diagrama));

    }



}
