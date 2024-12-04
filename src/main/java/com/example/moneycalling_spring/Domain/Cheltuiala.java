package com.example.moneycalling_spring.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="cheltuiala")
public class Cheltuiala extends Entitate {

     private String nume;

     private float suma;

     @Enumerated(EnumType.STRING)
     @Column(name = "tip_cheltuiala", nullable = false)
     private TipCheltuiala tipCheltuiala;


     @ManyToOne
     @JoinColumn(name = "id_diagrama")//cheia straina
     @JsonBackReference
     private Diagrama diagrama;

     public Diagrama getDiagrama() {
          return diagrama;
     }

     public void setDiagrama(Diagrama diagrama) {
          this.diagrama = diagrama;
     }

     public Cheltuiala(){

     }

     public Cheltuiala(int id, String nume, float suma ,TipCheltuiala tipCheltuiala, Diagrama diagrama)
     {
          super(id);
          this.nume=nume;
          this.suma=suma;
          this.diagrama = diagrama;
          this.tipCheltuiala=tipCheltuiala;
     }

     public TipCheltuiala getTipCheltuiala() {
          return tipCheltuiala;
     }

     public void setTipCheltuiala(TipCheltuiala tipCheltuiala) {
          this.tipCheltuiala = tipCheltuiala;
     }

     public String getNume() {
          return nume;
     }

     public void setNume(String nume) {
          this.nume = nume;
     }

     public float getSuma() {
          return suma;
     }

     public void setSuma(float suma) {
          this.suma = suma;
     }

     // Enum-ul TipCheltuiala definit în interiorul clasei Cheltuiala
     public enum TipCheltuiala {
          LOCUINTA(30f),
          ALIMENTAITE(15f),
          TRANSPORT(15f),
          DIVERTISMENT(10f),
          EDUCATIE(5f),
          ECONOMII(10f),
          SANATATE(10f),
          IMBRACAMINTE(5f);

          private float procent; // Procentul asociat acestui tip

          TipCheltuiala(float procent) {
               this.procent = procent;
          }

          public float getProcent() {
               return procent;
          }

          public void setProcent(float procent) {
               this.procent = procent;
          }
     }
}
