/*package Tests.domainTests;

import Domain.ProfilFinanciar;
import Domain.ProfilFinanciarConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilFinanciarConvTests {

    @Test
    public void testtoString(){
        ProfilFinanciar pf = new ProfilFinanciar(1, 500.0F, "City", 700.0F, 15);

        ProfilFinanciarConverter pfc = new ProfilFinanciarConverter();

        String expected = "1, 500.0, City, 700.0, 15";

        assertEquals(expected, pfc.toString(pf));
    }

    @Test
    public void testfromString(){
        String line = "1, 500.0F, City, 700.0F, 15";

        ProfilFinanciarConverter pfc = new ProfilFinanciarConverter();

        ProfilFinanciar pf = pfc.fromString(line);

        assertEquals(1, pf.getId(), "profilFinanciar id must be 1");
        assertEquals(500.0F, pf.getVenit(), "profilFinanciar venit must be 500.0F");
        assertEquals("City", pf.getDomiciliu(), "profilFinanciar domiciliu must be City");
        assertEquals(700.0F, pf.getContainerEconomii(), "profilFinanciar containerEconomii must be 700.0F");
        assertEquals(15, pf.getDataSalar(), "profilFinanciar dataSalar must be 15");
    }

}*/
