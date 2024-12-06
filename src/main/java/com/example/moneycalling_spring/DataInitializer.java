package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Data;
import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
@Component
public class DataInitializer implements CommandLineRunner {

    //ASTA ESTE UN EXEMPLU PT A VERIFICA DACA ADAUGA ELEMENTE IN BAZA DE DATE.
    @Autowired
    public UtilizatorRepository utilizatorRepository;
    //public CheltuialaRepository cheltuialaRepository;

    @Override
    public void run(String... args) throws Exception {
        Data data = new Data(15,10,2000);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",data,"mascul","0777333222", profil);
        //Cheltuiala cheltuiala = new Cheltuiala(1, "chirie", 1500.0f);
        utilizatorRepository.save(utilizator);
        //cheltuialaRepository.save(cheltuiala);
    }
}*/

