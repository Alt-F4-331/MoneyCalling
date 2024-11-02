package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository utilrepo;

    public Utilizator saveUtilizator(Utilizator utilizator)
    {

        return utilrepo.save(utilizator);
        //aceasta metoda poate fi folosita si pentru update:
        //-daca este creata un user cu un id nou il creeaza
        //-daca exista deja acel id,il actualizeaza
    }

    public void stergeUtilizatorById(int id){
        utilrepo.deleteById(id);
        //sterge un user dupa un id
    }

    public List<Utilizator> getAllUtilizatori(){

        return utilrepo.findAll();
        //returneaza toti utilizatorii
    }

    public void deleteAll(){
        utilrepo.deleteAll();
        //sterge toti userii
    }

    //metode extra dupa

}
