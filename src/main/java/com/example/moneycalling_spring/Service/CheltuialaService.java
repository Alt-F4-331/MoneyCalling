package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Repository.CheltuialaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.moneycalling_spring.Domain.Diagrama;
import java.util.List;

@Service
public class CheltuialaService {

    @Autowired
    private CheltuialaRepository cheltuialaRepository;

    @Autowired
    public CheltuialaService(CheltuialaRepository cheltuiala)
    {
        this.cheltuialaRepository = cheltuiala;
    }

    //Metoda care adauga o noua cheltuiala
    public Cheltuiala saveCheltuiala(Cheltuiala cheltuiala)
    {
        return cheltuialaRepository.save(cheltuiala);
        //aceasta metoda poate fi folosita si pentru update:
        //-daca este creata o noua cheltuiala cu un id nou il creeaza
        //-daca exista deja acel id,il actualizeaza
    }

    //Metoda care returneaza toate cheltuielile
    public List<Cheltuiala> getAllCheltuieli(){
        return cheltuialaRepository.findAll();
    }

    public void stergeCheltuialaById(int id){
        cheltuialaRepository.deleteById(id);
    }

    // Metodă pentru a șterge toate cheltuielile
    public void deleteAll() {
        cheltuialaRepository.deleteAll();
    }

   List<Cheltuiala> getAllCheltuieliByIdDiagrama(Diagrama diagrama){
        return cheltuialaRepository.findByDiagrama(diagrama);
   }


        //metode extra dupa

}
