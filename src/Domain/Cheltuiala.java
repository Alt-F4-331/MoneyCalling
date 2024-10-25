package Domain;

public class Cheltuiala extends Entitate {

     private String nume;

     private float suma;

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
