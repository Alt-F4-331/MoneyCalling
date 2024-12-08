package com.example.moneycalling_spring.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(
        name = "diagrama",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_user", "luna", "an"})
)
public class Diagrama extends Entitate {

    @ManyToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    @JsonBackReference
    private Utilizator user;

    @OneToMany(mappedBy = "diagrama", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Cheltuiala> listaCheltuieli ;

    @OneToMany(mappedBy = "diagrama", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Raport> listaRapoarte ;// legatura cu raportul
    @Embedded
    private Data dataDiagrama;

    @ElementCollection
    @CollectionTable(name = "procente_diagrama", joinColumns = @JoinColumn(name = "id_diagrama"))
    @MapKeyColumn(name = "tip_cheltuiala")
    @Column(name = "procent_ramas")
    @Enumerated(EnumType.STRING)
    private Map<Cheltuiala.TipCheltuiala, Float> procenteCheltuieli = new HashMap<>();

    @Column(name = "activa")
    private Boolean activa; // Indică dacă diagrama este activă

    public Diagrama(int id, @Valid Data data,@Valid Utilizator User, boolean activ) {
        super(id);
        this.dataDiagrama = data;
        this.user = User;
        this.activa = activ;
    }

    public Diagrama()
    {

    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Map<Cheltuiala.TipCheltuiala, Float> getProcenteCheltuieli() {
        return procenteCheltuieli;
    }

    public void setProcenteCheltuieli(Map<Cheltuiala.TipCheltuiala, Float> procenteCheltuieli) {
        this.procenteCheltuieli = procenteCheltuieli;
    }

    public void initializeProcente(Diagrama diagrama , float sumaEconomii) {
        for (Cheltuiala.TipCheltuiala tip : Cheltuiala.TipCheltuiala.values()) {
            if (!tip.equals(Cheltuiala.TipCheltuiala.CONTAINER)) { // Exclude CONTAINER
                diagrama.getProcenteCheltuieli().put(tip, tip.getProcent());
            }
            else
                diagrama.getProcenteCheltuieli().put(tip,sumaEconomii);

            //pt tipul container,salvam suma pentru raportul de economii

        }
    }


    public void setUser(Utilizator user) {
        this.user = user;
    }

    public Utilizator getUser() {
        return user;
    }

    public Data getDataDiagrama() {
        return dataDiagrama;
    }

    public void setDataDiagrama(Data dataDiagrama) {
        this.dataDiagrama = dataDiagrama;
    }

    public List<Cheltuiala> getListaCheltuieli() {
        return listaCheltuieli;
    }

}
