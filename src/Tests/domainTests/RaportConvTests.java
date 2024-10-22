package Tests.domainTests;

import Domain.Raport;
import Domain.RaportConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaportConvTests {
    @Test
    public void testtoString(){
        Raport raport = new Raport(1, 2);

        RaportConverter raportconverter = new RaportConverter();

        String expected = "1, 2";

        assertEquals(expected, raportconverter.toString(raport));
    }

    @Test
    public void testfromString(){
        // TODO : eroare la scriere from string nu vede al 2 lea token corec
        String linie = "1, 2";

        RaportConverter converter = new RaportConverter();

        Raport raport = converter.fromString(linie);

        assertEquals(1, raport.getId(), "raport id must be 1");
        assertEquals(2, raport.getId(), "raport idDiagrama must be 2");
    }
}
