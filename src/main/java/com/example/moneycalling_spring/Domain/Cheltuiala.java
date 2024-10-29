package com.example.moneycalling_spring.Domain;

import jakarta.persistence.*;


public class Cheltuiala extends Entitate {

     private String nume;

     private float suma;


    // private Diagrama diagrama;  asta este doar pentru tabela,pentru cheie straina,nu e atribut pentru cheltuiala

     public Cheltuiala(){

     }

     public Cheltuiala(int id, String nume, float suma)
     {
          super(id);
          this.nume=nume;
          this.suma=suma;
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
