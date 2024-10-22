package Tests.domainTests;

import Domain.ProfilFinanciar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilFinanciarTests {
    private ProfilFinanciar pf;

    @BeforeEach
    public void setUp(){
        pf = new ProfilFinanciar(1, 500, "City", 700, 15);
    }

    @Test
    public void testConstructor(){
        assertEquals(1, pf.getId(), "ProfilFinanciar id must be 1");
        assertEquals(500, pf.getVenit(), "ProfilFinanciar venit must be 500");
        assertEquals("City", pf.getDomiciliu(), "ProfilFinanciar domiciliu must be City");
        assertEquals(700, pf.getContainerEconomii(), "ProfilFinanciar containerEconomii must be 700");
        assertEquals(15, pf.getDataSalar(), "ProfilFinanciar dataSalar must be 15");
    }

    @Test
    public void testsetVenit(){
        pf.setVenit(600);
        assertEquals(600, pf.getVenit(), "ProfilFinanciar venit must be 600");
    }

    @Test
    public void testsetDomiciliu(){
        pf.setDomiciliu("newCity");
        assertEquals("newCity", pf.getDomiciliu(), "ProfilFinanciar vomiciliu must be newCity");
    }

    @Test
    public void testsetContainerEconomii(){
        pf.setContainerEconomii(800);
        assertEquals(800, pf.getContainerEconomii(), "ProfilFinanciar containerEconomii must be 800");
    }

    @Test
    public void testsetDataSalar(){
        pf.setDataSalar(20);
        assertEquals(20, pf.getDataSalar(), "ProfilFinanciar dataSalar must be 20");
    }
}

