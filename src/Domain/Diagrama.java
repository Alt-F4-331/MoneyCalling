package Domain;

import java.util.ArrayList;

public class Diagrama extends Entitate {

    private int idUser;
    private int dataSalar;
    
    private ArrayList<Cheltuiala> listaCheltuieli;

    public Diagrama(int id, int idUser, int dataSalariu, ArrayList<Cheltuiala> listaCheltuieli) {
        super(id);
        this.idUser = idUser;
        this.dataSalar = dataSalariu;
        this.listaCheltuieli = listaCheltuieli;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getDataSalar() {
        return dataSalar;
    }

    public void setDataSalar(int dataSalar) {
        this.dataSalar = dataSalar;
    }

    public ArrayList<Cheltuiala> getListaCheltuieli() {
        return listaCheltuieli;
    }

    public void setListaCheltuieli(ArrayList<Cheltuiala> listaCheltuieli) {
        this.listaCheltuieli = listaCheltuieli;
    }
}
