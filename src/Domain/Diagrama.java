package Domain;

import java.util.ArrayList;

public class Diagrama extends Entitate {

    private final int idUser;
    
    private ArrayList<Cheltuiala> listaCheltuieli;

    public Diagrama(int id, int idUser, ArrayList<Cheltuiala> listaCheltuieli) {
        super(id);
        this.idUser = idUser;
        this.listaCheltuieli = listaCheltuieli;
    }

    public int getIdUser() {
        return idUser;
    }

    public ArrayList<Cheltuiala> getListaCheltuieli() {
        return listaCheltuieli;
    }

    public void setListaCheltuieli(ArrayList<Cheltuiala> listaCheltuieli) {
        this.listaCheltuieli = listaCheltuieli;
    }
}
