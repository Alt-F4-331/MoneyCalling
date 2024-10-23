package Tests.domainTests;

import Domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilizatorConvTests {

    @Test
    public void testtoString(){
        Data data = new Data(1,1,2000);
        ProfilFinanciar pf = new ProfilFinanciar(1, 300, "City", 500, 15);
        Utilizator user = new Utilizator(1, "LastName", "FirstName", "password","email@domain.com", data, "0123456789", pf.getId());

        UtilizatorConverter utc = new UtilizatorConverter();

        String expected = "1, LastName, FirstName, password, email@domain.com, 1.1.2000, 0123456789, 1";

        assertEquals(expected, utc.toString(user));
    }

    @Test
    public void testfromString(){
        String line = "1, LastName, FirstName, password, email@domain.com, 1.1.2000, 0123456789, 1";

        UtilizatorConverter utc = new UtilizatorConverter();

        Utilizator user = utc.fromString(line);

        assertEquals(1, user.getId(), "Utilizator id must be 1");

    }
}
