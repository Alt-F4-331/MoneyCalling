package Domain;

public class Raport extends Entitate {

    private int idDiagrama;

     public Raport(int id, int idDiagrama)
     {
         super(id);
         this.idDiagrama = idDiagrama;

     }
    public int getIdDiagrama() {
        return idDiagrama;
    }

    public void setIdDiagrama(int idDiagrama) {
        this.idDiagrama = idDiagrama;
    }
}
