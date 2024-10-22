package Tests.domainTests;

import Domain.ProfilFinanciar;
import Domain.ProfilFinanciarConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilFinanciarConvTests {

    @Test
    public void testtoString(){
        ProfilFinanciar pf = new ProfilFinanciar(1, 500, "City", 700, 15);

        ProfilFinanciarConverter pfc = new ProfilFinanciarConverter();

        String expected = "1,500,City,700,15";

        assertEquals(expected, pfc.toString(pf));
    }

    @Test
    public void testfromString(){
        // TODO -fromString()- FUNCTIA DE FROMSTRING DIN PROFILFINANCIARCONVERTER DA NULL ---- NU AM TEST
    }

}
