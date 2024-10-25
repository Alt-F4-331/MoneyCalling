package Tests.domainTests;

import Domain.ProfilFinanciar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilFinanciarTests {
    private ProfilFinanciar pf;

    @BeforeEach
    public void setUp(){
        pf = new ProfilFinanciar(1, 500.0F, "City", 700.0F, 15);
    }

    @Test
    public void testConstructor(){
        assertEquals(1, pf.getId(), "ProfilFinanciar id must be 1");
        assertEquals(500.0F, pf.getVenit(), "ProfilFinanciar venit must be 500.0F");
        assertEquals("City", pf.getDomiciliu(), "ProfilFinanciar domiciliu must be City");
        assertEquals(700.0F, pf.getContainerEconomii(), "ProfilFinanciar containerEconomii must be 700.0F");
        assertEquals(15, pf.getDataSalar(), "ProfilFinanciar dataSalar must be 15");
    }

    @Test
    public void testsetVenit(){
        pf.setVenit(600.0F);
        assertEquals(600.0F, pf.getVenit(), "ProfilFinanciar venit must be 600.0F");
    }

    @Test
    public void testsetDomiciliu(){
        pf.setDomiciliu("newCity");
        assertEquals("newCity", pf.getDomiciliu(), "ProfilFinanciar domiciliu must be newCity");
    }

    @Test
    public void testsetContainerEconomii(){
        pf.setContainerEconomii(800.0F);
        assertEquals(800.0F, pf.getContainerEconomii(), "ProfilFinanciar containerEconomii must be 800.0F");
    }

    @Test
    public void testsetDataSalar(){
        pf.setDataSalar(20);
        assertEquals(20, pf.getDataSalar(), "ProfilFinanciar dataSalar must be 20");
    }
}

