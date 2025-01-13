package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Cheltuiala;
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

    public Raport saveRaport(Raport raport) {
        return raportRepository.save(raport);
        //aceasta metoda poate fi folosita si pentru update:
        //-daca este creata un raport cu un id nou il creeaza
        //-daca exista deja acel id,il actualizeaza
    }

    public void stergeRaportById(int id) {
        raportRepository.deleteById(id);
        //sterge raport dupa id
    }

    public float sugereazaChirieByVenit(float suma, Diagrama diagrama) {
        float procent = 85;
        //aici vrem ca dupa plata chiriei,sa ramana 5% din venit alocat pentru alte lucruri in locuinta
        return (procent * suma) / 100;
    }

    public float sugereazaRataByVenit(float suma, int ani) {
        float dobanda = 0.05f;
        float sumaTotala = suma + suma * dobanda * ani;
        float numarRate = ani * 12;
        float valoareRate = sumaTotala / numarRate;
        return valoareRate;
    }

    public List<Raport> getAllRapoarteByDiagrama(Diagrama diagrama) {
        return raportRepository.findByDiagrama(diagrama);
        //returneaza toate rapoartele dupa o diagrama
    }

    public void deleteAllRapoarteByDiagrama(Diagrama diagrama) {
        raportRepository.deleteAllByDiagrama(diagrama);
    }

    public Optional<Raport> getById(int id) {
        return raportRepository.findById(id);
    }


    public void stocheazaChiriePropusa(int userId, float chiriePropusa) {
        chiriiPropuse.put(userId, chiriePropusa);
    }

    public Optional<Float> getChiriePropusa(int userId) {
        return Optional.ofNullable(chiriiPropuse.get(userId));
    }

    public void eliminaChiriePropusa(int userId) {
        chiriiPropuse.remove(userId);
    }
}