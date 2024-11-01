package com.example.moneycalling_spring.Domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diagrama") //numele tabelului
public class Diagrama extends Entitate {

    @ManyToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    private Utilizator user;

    @OneToMany(mappedBy = "diagrama", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<Cheltuiala> listaCheltuieli ;

    @OneToMany(mappedBy = "diagrama", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<Raport> listaRapoarte ;// legatura cu raportul

    public Diagrama(int id, Utilizator idUser, ArrayList<Cheltuiala> listaCheltuieli) {
        super(id);
        this.user = idUser;
        this.listaCheltuieli = listaCheltuieli;
    }

    public Diagrama()
    {

    }


    public Utilizator getIdUser() {
        return user;
    }


    public ArrayList<Cheltuiala> getListaCheltuieli() {
        return listaCheltuieli;
    }

    public void setListaCheltuieli(ArrayList<Cheltuiala> listaCheltuieli) {
        this.listaCheltuieli = listaCheltuieli;
    }
}
