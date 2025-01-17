package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Repository.CheltuialaRepository;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.moneycalling_spring.Domain.Diagrama;

import jakarta.validation.Valid;
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

    // Metoda care adauga o noua cheltuiala
    public Cheltuiala saveCheltuiala(@Valid Cheltuiala cheltuiala) {
        return cheltuialaRepository.save(cheltuiala);
        // Aceasta metoda poate fi folosita si pentru update:
        // - daca este creata o noua cheltuiala cu un ID nou, o creeaza
        // - daca exista deja acel ID, il actualizeaza
    }

    // Metoda care returneaza toate cheltuielile
    public List<Cheltuiala> getAllCheltuieli() {
        return cheltuialaRepository.findAll();
        // Pentru admin?
    }

    // Metoda care returneaza primul ID disponibil
    public int getFirstAvailableId() {
        List<Integer> allIds = cheltuialaRepository.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    // Metoda pentru obtinerea unei cheltuieli dupa ID
    public Optional<Cheltuiala> getById(int id) {
        return cheltuialaRepository.findById(id);
    }

    // Metoda pentru stergerea unei cheltuieli dupa ID
    public void stergeCheltuialaById(int id) {
        cheltuialaRepository.deleteById(id);
    }

    // Metoda pentru stergerea tuturor cheltuielilor
    public void deleteAll() {
        cheltuialaRepository.deleteAll();
    }

    // Metoda pentru obtinerea tuturor cheltuielilor dupa diagrama
    public List<Cheltuiala> getAllCheltuieliByIdDiagrama(Diagrama diagrama) {
        return cheltuialaRepository.findByDiagrama(diagrama);
    }
}