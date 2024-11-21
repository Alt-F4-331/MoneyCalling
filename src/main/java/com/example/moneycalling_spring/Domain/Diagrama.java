package com.example.moneycalling_spring.Domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "diagrama") //numele tabelului
public class Diagrama extends Entitate {

    @ManyToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    private Utilizator user;

    private Data dataDiagrama;

    @OneToMany(mappedBy = "diagrama", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cheltuiala> listaCheltuieli ;

    @OneToMany(mappedBy = "diagrama", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Raport> listaRapoarte ;// legatura cu raportul
    public Diagrama(int id, Utilizator idUser) {
        super(id);
        this.user = idUser;
    }

    public Diagrama()
    {

    }

    public void setUser(Utilizator user) {
        this.user = user;
    }

    public Utilizator getUser() {
        return user;
    }


    public List<Cheltuiala> getListaCheltuieli() {
        return listaCheltuieli;
    }

}
