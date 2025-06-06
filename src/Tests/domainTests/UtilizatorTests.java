package Tests.domainTests;

import Domain.Data;
import Domain.ProfilFinanciar;
import Domain.Utilizator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilizatorTests {
    private Utilizator user;
    private Data data;
    private ProfilFinanciar pf;

    @BeforeEach
    public void setUp() {
        data = new Data(1,1,2000);
        pf = new ProfilFinanciar(1, 300.0F, "City", 500.0F, 15);
        user = new Utilizator(1, "LastName", "FirstName", "password","email@domain.com", data, "man", "0123456789", pf);
    }

    @Test
    public void testConstructor(){
        assertEquals(1, user.getId(), "user id must be 1");
        assertEquals("LastName", user.getNume(), "user name must be LastName");
        assertEquals("FirstName", user.getPrenume(), "user prenume must be FirstName");
        assertEquals("password", user.getParola(), "user password must be password");
        assertEquals("email@domain.com", user.getEmail(), "user email must be email@domain.com");
        assertEquals(1, user.getDataNasterii().getZi(), "user zi must be 1");
        assertEquals(1, user.getDataNasterii().getLuna(), "user luna must be 1");
        assertEquals(2000, user.getDataNasterii().getAn(), "user an must be 2000");
        assertEquals("man", user.getSex(), "user sex must be man");
        assertEquals("0123456789", user.getNumarTelefon(), "user NumarTelefon must be 0123456789");
        assertEquals(pf.getId(), user.getProfil().getId(), "user profil id must be 1");
        assertEquals(pf.getVenit(), user.getProfil().getVenit(), "user profil venit must be 300.0F");
        assertEquals(pf.getDomiciliu(), user.getProfil().getDomiciliu(), "user profil domiciliu must be City");
        assertEquals(pf.getContainerEconomii(), user.getProfil().getContainerEconomii(), "user containerEconomii must be 500.0F");
        assertEquals(pf.getDataSalar(), user.getProfil().getDataSalar(), "user profil dataSalar must be 15");
    }

    @Test
    public void testSetNume(){
        user.setNume("newLastName");
        assertEquals("newLastName", user.getNume(), "user nume must be newLastName");
    }

    @Test
    public void testSetPrenume(){
        user.setPrenume("newPrenume");
        assertEquals("newPrenume", user.getPrenume(), "prenume must be newPrenume");
    }

    @Test
    public void testSetPassword(){
        user.setParola("newPassword");
        assertEquals("newPassword", user.getParola(), "password must be newPassword");
    }

    @Test
    public void testSetEmail(){
        user.setEmail("newEmail");
        assertEquals("newEmail", user.getEmail(), "email must be newEmail");
    }

    @Test
    public void testSetDataNasterii(){
        Data datanew = new Data(2,3,2004);
        user.setDataNasterii(datanew);
        assertEquals(2, user.getDataNasterii().getZi(), "user zi must be 2");
        assertEquals(3, user.getDataNasterii().getLuna(), "user luna must be 3");
        assertEquals(2004, user.getDataNasterii().getAn(), "user an must be 2004");
    }

    @Test
    public void testsetSex(){
        user.setSex("woman");
        assertEquals("woman", user.getSex(), "user sex must be woman");
    }

    @Test
    public void testSetNumarTelefon(){
        user.setNumarTelefon("newTelefon");
        assertEquals("newTelefon", user.getNumarTelefon(), "telefon must be newTelefon");
    }

    @Test
    public void testSetProfil(){
        ProfilFinanciar pfnew = new ProfilFinanciar(2, 400.0F, "Town", 600.0F, 20);
        user.setProfil(pfnew);
        assertEquals(pfnew, user.getProfil(), "idprofil must be pfnew");
    }
}
