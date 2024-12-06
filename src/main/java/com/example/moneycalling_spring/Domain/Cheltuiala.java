package com.example.moneycalling_spring.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "cheltuiala")
public class Cheltuiala extends Entitate {

     @NotBlank(message = "Numele cheltuielii nu poate fi gol.")
     @Size(max = 100, message = "Numele cheltuielii poate avea cel mult 100 de caractere.")
     private String nume;

     @Positive(message = "Suma trebuie să fie un număr pozitiv.")
     private float suma;

     @Enumerated(EnumType.STRING)
     @Column(name = "tip_cheltuiala", nullable = false)
     @NotNull(message = "Tipul cheltuielii este obligatoriu.")
     private TipCheltuiala tipCheltuiala;

     @ManyToOne
     @JoinColumn(name = "id_diagrama") // cheia străină
     @JsonBackReference
     @NotNull(message = "Diagrama asociată cheltuielii nu poate fi nulă.")
     private Diagrama diagrama;

     // Constructori, getter/setteri etc.
     public Cheltuiala() {}

     public Cheltuiala(int id, String nume, float suma, TipCheltuiala tipCheltuiala, Diagrama diagrama) {
          super(id);
          this.nume = nume;
          this.suma = suma;
          this.diagrama = diagrama;
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

     public TipCheltuiala getTipCheltuiala() {
          return tipCheltuiala;
     }

     public void setTipCheltuiala(TipCheltuiala tipCheltuiala) {
          this.tipCheltuiala = tipCheltuiala;
     }

     public Diagrama getDiagrama() {
          return diagrama;
     }

     public void setDiagrama(Diagrama diagrama) {
          this.diagrama = diagrama;
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Cheltuiala that = (Cheltuiala) o;
          return Float.compare(that.suma, suma) == 0 &&
                  Objects.equals(nume, that.nume) &&
                  tipCheltuiala == that.tipCheltuiala &&
                  Objects.equals(diagrama, that.diagrama);
     }

     @Override
     public int hashCode() {
          return Objects.hash(nume, suma, tipCheltuiala, diagrama);
     }

     // Enum-ul TipCheltuiala definit în interiorul clasei Cheltuiala
     public enum TipCheltuiala {
          LOCUINTA(30f),
          ALIMENTATIE(15f),
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
