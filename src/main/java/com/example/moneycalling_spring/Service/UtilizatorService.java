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

    public Utilizator createUtilizator(Utilizator utilizator)
    {
        return utilrepo.save(utilizator);
    }

    public List<Utilizator> getAllUtilizatori(){
        return utilrepo.findAll();
    }

    //metode extra dupa

}
