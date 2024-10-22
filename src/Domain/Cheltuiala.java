package Domain;

public class Cheltuiala extends Entitate {

     private String nume;

     private int suma;

     public Cheltuiala(int id, String nume, int suma)
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

     public int getSuma() {
          return suma;
     }

     public void setSuma(int suma) {
          this.suma = suma;
     }
}
