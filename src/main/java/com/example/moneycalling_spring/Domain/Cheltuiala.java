package com.example.moneycalling_spring.Domain;

import jakarta.persistence.*;

@Entity
@Table(name="cheltuiala")
public class Cheltuiala extends Entitate {

     private String nume;

     private float suma;

     private float procent;


     @ManyToOne
     @JoinColumn(name = "id_diagrama")//cheia straina
     private Diagrama diagrama;

     public Diagrama getDiagrama() {
          return diagrama;
     }

     public void setDiagrama(Diagrama diagrama) {
          this.diagrama = diagrama;
     }

     public Cheltuiala(){

     }

     public Cheltuiala(int id, String nume, float suma ,float procent, Diagrama diagrama)
     {
          super(id);
          this.nume=nume;
          this.suma=suma;
          this.diagrama = diagrama;
          this.procent = procent;
     }

     public float getProcent() {
          return procent;
     }

     public void setProcent(float procent) {
          this.procent = procent;
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
}
