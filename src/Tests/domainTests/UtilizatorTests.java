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
        pf = new ProfilFinanciar(1, 300, "City", 500, 15);
        user = new Utilizator(1, "LastName", "FirstName", "password","email@domain.com", data, "0123456789", pf.getId());
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
        assertEquals("0123456789", user.getNumarTelefon(), "user NumarTelefon must be 0123456789");
        assertEquals(1, user.getProfil(), "user profil must be 1");
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
    public void testSetEmail(){
        user.setEmail("newEmail");
        assertEquals("newEmail", user.getEmail(), "email must be newEmail");
    }

    @Test
    public void testSetNumarTelefon(){
        user.setNumarTelefon("newTelefon");
        assertEquals("newTelefon", user.getNumarTelefon(), "telefon must be newTelefon");
    }

    @Test
    public void testSetProfil(){
        user.setProfil(2);
        assertEquals(2, user.getProfil(), "idprofil must be 2");
    }
}
