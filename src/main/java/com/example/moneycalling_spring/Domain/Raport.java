package com.example.moneycalling_spring.Domain;
import jakarta.persistence.*;
@Entity
@Table(name = "raport")
public class Raport extends Entitate {

    @ManyToOne // Relația ManyToOne cu Diagrama
    @JoinColumn(name = "id_diagrama") // Numele coloanei care face legătura
    private Diagrama diagrama;

     public Raport(int id, Diagrama idDiagrama)
     {
         super(id);
         this.diagrama = idDiagrama;

     }

    public Raport() {

    }

    public Diagrama getIdDiagrama() {
        return diagrama;
    }

    public void setIdDiagrama(Diagrama idDiagrama) {
        this.diagrama = idDiagrama;
    }
}
