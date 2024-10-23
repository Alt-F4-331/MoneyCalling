package Tests.domainTests;

import Domain.Cheltuiala;
import Domain.Diagrama;
import Domain.DiagramaConverter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagramaConvTests {

    @Test
    public void testtoString(){
        // TODO: trebuie refacut toString lipsesc niste virgule si spatii
        //  momentan arata 1,1food,70
        //  ar trebui (id_diag)1, (id_prod)1, (numec)food, (pretc)70
        ArrayList<Cheltuiala> lista = new ArrayList<Cheltuiala>();
        lista.add(new Cheltuiala(1, "food", 70));
        lista.add(new Cheltuiala(2, "transport", 40));
        //lista.add(new Cheltuiala(3, "health", 60));
        Diagrama diag = new Diagrama(1, 1, lista);

        DiagramaConverter diagc = new DiagramaConverter();

        String expected = "1,1food,70,2transport,40";

        assertEquals(expected, diagc.toString(diag));
    }

    @Test
    public void testfromString(){
        String line = "1,1,1,food,70,2,health,60";

        DiagramaConverter diagc = new DiagramaConverter();

        Diagrama diag = diagc.fromString(line);

        assertEquals(1, diag.getId(), "Diagrama id must be 1");
        assertEquals(1, diag.getIdUser(), "Diagrama user id must be 1");
        assertEquals(1, diag.getListaCheltuieli().getFirst().getId(), "1st cheltuiala id must be 1");
        assertEquals(2, diag.getListaCheltuieli().get(1).getId(), "2nd cheltuiala id must be 2");
        assertEquals("food", diag.getListaCheltuieli().getFirst().getNume(), "1st cheltuiala name must be food");
        assertEquals(70, diag.getListaCheltuieli().getFirst().getSuma(), "1st cheltuiala suma must be 70");
        assertEquals("health", diag.getListaCheltuieli().get(1).getNume(), "2nd cheltuiala name must be health");
        assertEquals(60, diag.getListaCheltuieli().get(1).getSuma(), "2nd cheltuiala suma must be 60");
    }
}
