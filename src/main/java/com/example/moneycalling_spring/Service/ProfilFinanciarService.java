package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Repository.ProfilFinanciarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilFinanciarService {
    @Autowired
    private ProfilFinanciarRepository profilrepo;

    @Autowired
    public ProfilFinanciarService(ProfilFinanciarRepository pf) {
        this.profilrepo = pf;
    }

    // Metoda pentru salvarea unui profil financiar
    public ProfilFinanciar saveProfilFinanciar(ProfilFinanciar pf) {
        return profilrepo.save(pf);
        // Creeaza un nou obiect de tip profil financiar daca ID-ul nu exista,
        // iar daca exista, modifica obiectul cu ID-ul dat
    }

    // Metoda pentru stergerea unui profil financiar dupa ID
    public void stergeProfilFinanciarById(int id) {
        profilrepo.deleteById(id);
        // Sterge un profil dupa ID
    }

    // Metoda pentru obtinerea primului ID disponibil
    public int getFirstAvailableId() {
        List<Integer> allIds = profilrepo.findAllIds();
        int id = 1;
        while (allIds.contains(id)) {
            id++;
        }
        return id;
    }

    // Metoda pentru obtinerea tuturor profilurilor financiare
    public List<ProfilFinanciar> getAllProfiluriFinanciare() {
        return profilrepo.findAll();
        // Returneaza toate profilurile financiare (doar pentru administrator?)
    }

    // Metoda pentru stergerea tuturor profilurilor financiare
    public void deleteAll() {
        profilrepo.deleteAll();
        // Sterge toate profilurile financiare
    }

    // Metode suplimentare pot fi adaugate aici
}