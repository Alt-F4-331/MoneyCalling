package Tests.domainTests;

import Domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagramaTests {
    private Diagrama diagrama;
    private Utilizator user;
    private Data data;
    ArrayList<Cheltuiala> lista = new ArrayList<Cheltuiala>();

    @BeforeEach
    public void setUp() {
        lista.add(new Cheltuiala(1, "food", 70));
        lista.add(new Cheltuiala(2, "transport", 40));
        lista.add(new Cheltuiala(3, "health", 50));
        data = new Data(1,1,2000);
        ProfilFinanciar profil = new ProfilFinanciar(1, 4000.0F,"Cluj",15000,15);
        user = new Utilizator(1, "LastName", "FirstName", "password","email@domain.com", data,"man", "0123456789", profil);

        diagrama = new Diagrama(1, user.getId(), lista);
    }

    @Test
    public void testConstructor(){
        assertEquals(1, diagrama.getId(), "diagrama id must be 1");
        assertEquals(user.getId(), diagrama.getIdUser(), "diagrama userid must be 1");
        assertEquals(lista.get(1).getId(), diagrama.getListaCheltuieli().get(1).getId(), "diagrama listacheltuieli with the element on index 1 must be 1");
    }

    @Test
    public void testsetLista(){
        lista.add(new Cheltuiala(4, "shoes", 90));
        diagrama.setListaCheltuieli(lista);
        assertEquals(4, diagrama.getListaCheltuieli().get(3).getId(), "diagrama userid must be 4");
    }
}
