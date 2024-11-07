package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {

    @Autowired
    private UtilizatorRepository utilrepo;

    @Autowired
    public UtilizatorService(UtilizatorRepository utilizatorRepository){
        this.utilrepo=utilizatorRepository;
    }

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

    public Optional<Utilizator> getById(int id)
    {
        return utilrepo.findById(id);
    }

    public List<Utilizator> getAllUtilizatori(){

        return utilrepo.findAll();
        //returneaza toti utilizatorii
    }

    public void deleteAll(){
        utilrepo.deleteAll();
        //sterge toti userii
    }

    public Optional<Utilizator> getByEmail(String email)
    {
        return utilrepo.findByEmail(email);
    }


    //metode extra dupa

}
