package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository utilrepo;

    @Autowired
    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilrepo = utilizatorRepository;
    }

    // Metoda pentru salvarea unui utilizator
    public Utilizator saveUtilizator(@Valid Utilizator utilizator) {
        return utilrepo.save(utilizator);
        // Aceasta metoda poate fi folosita si pentru update:
        // - daca este creat un utilizator cu un ID nou, il creeaza
        // - daca exista deja acel ID, il actualizeaza
    }

    // Metoda care returneaza primul ID disponibil
    public int getFirstAvailableId() {
        List<Integer> allIds = utilrepo.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    // Metoda pentru stergerea unui utilizator dupa ID
    public void stergeUtilizatorById(int id) {
        utilrepo.deleteById(id);
        // Sterge un utilizator dupa ID
    }

    // Metoda pentru obtinerea unui utilizator dupa ID
    public Optional<Utilizator> getById(int id) {
        return utilrepo.findById(id);
    }

    // Metoda pentru obtinerea tuturor utilizatorilor
    public List<Utilizator> getAllUtilizatori() {
        return utilrepo.findAll();
        // Returneaza toti utilizatorii
    }

    // Metoda pentru stergerea tuturor utilizatorilor
    public void deleteAll() {
        utilrepo.deleteAll();
        // Sterge toti utilizatorii
    }

    // Metoda pentru obtinerea unui utilizator dupa email
    public Optional<Utilizator> getByEmail(String email) {
        return utilrepo.findByEmail(email);
    }

    // Metode suplimentare pot fi adaugate aici
}