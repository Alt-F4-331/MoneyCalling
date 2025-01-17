package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Repository.ProfilFinanciarRepository;
import com.example.moneycalling_spring.Repository.RaportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RaportService {

    @Autowired
    private RaportRepository raportRepository;
    private ProfilFinanciarRepository profilFinanciarRepository;
    private final Map<Integer, Float> chiriiPropuse = new ConcurrentHashMap<>();

    @Autowired
    public RaportService(RaportRepository rap, ProfilFinanciarRepository prof) {
        this.raportRepository = rap;
        this.profilFinanciarRepository = prof;
    }

    // Metoda pentru salvarea unui raport
    public Raport saveRaport(Raport raport) {
        return raportRepository.save(raport);
        // Aceasta metoda poate fi folosita si pentru update:
        // - daca este creat un raport cu un ID nou, il creeaza
        // - daca exista deja acel ID, il actualizeaza
    }

    // Metoda pentru stergerea unui raport dupa ID
    public void stergeRaportById(int id) {
        raportRepository.deleteById(id);
        // Sterge raportul dupa ID
    }

    // Metoda pentru sugerarea chiriei in functie de venit
    public float sugereazaChirieByVenit(float suma, Diagrama diagrama) {
        float procent = 85;
        // Aici vrem ca dupa plata chiriei, sa ramana 5% din venit alocat pentru alte lucruri in locuinta
        return (procent * suma) / 100;
    }

    // Metoda pentru sugerarea ratei
    public float sugereazaRata(float suma, int luni) {
        float dobanda = 0.05f;
        float sumaTotala = suma + suma * dobanda * luni / 12;
        float valoareRate = sumaTotala / luni;
        valoareRate = Math.round(valoareRate * 100) / 100.0f;
        return valoareRate;
    }

    // Metoda pentru obtinerea tuturor rapoartelor dupa diagrama
    public List<Raport> getAllRapoarteByDiagrama(Diagrama diagrama) {
        return raportRepository.findByDiagrama(diagrama);
        // Returneaza toate rapoartele dupa o diagrama
    }

    // Metoda pentru stergerea tuturor rapoartelor dupa diagrama
    public void deleteAllRapoarteByDiagrama(Diagrama diagrama) {
        raportRepository.deleteAllByDiagrama(diagrama);
    }

    // Metoda pentru obtinerea unui raport dupa ID
    public Optional<Raport> getById(int id) {
        return raportRepository.findById(id);
    }

    // Metoda pentru stocarea chiriei propuse
    public void stocheazaChiriePropusa(int userId, float chiriePropusa) {
        chiriiPropuse.put(userId, chiriePropusa);
    }

    // Metoda pentru obtinerea chiriei propuse
    public Optional<Float> getChiriePropusa(int userId) {
        return Optional.ofNullable(chiriiPropuse.get(userId));
    }

    // Metoda pentru eliminarea chiriei propuse
    public void eliminaChiriePropusa(int userId) {
        chiriiPropuse.remove(userId);
    }
}