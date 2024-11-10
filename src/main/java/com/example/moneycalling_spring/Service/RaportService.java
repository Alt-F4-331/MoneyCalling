package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Repository.RaportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RaportService {

    @Autowired
    private RaportRepository raportRepository ;

    @Autowired
    public RaportService(RaportRepository rap)
    {
        this.raportRepository = rap;
    }

    public Raport saveRaport(Raport raport){
        return raportRepository.save(raport);
        //aceasta metoda poate fi folosita si pentru update:
        //-daca este creata un raport cu un id nou il creeaza
        //-daca exista deja acel id,il actualizeaza
    }

    public void stergeRaportById(int id)
    {
        raportRepository.deleteById(id);
        //sterge raport dupa id
    }


    public List<Raport> getAllRapoarteByIdDiagrama(Diagrama diagrama)
    {
        return raportRepository.findByDiagrama(diagrama);
        //returneaza toate rapoartele dupa o diagrama
    }
    public void deleteAllRapoarteByIdDIagrama(Diagrama diagrama)
    {
        raportRepository.deleteAllByDiagrama(diagrama);
    }
    public Optional<Raport> getById(int id)
    {
        return raportRepository.findById(id);
    }
}
