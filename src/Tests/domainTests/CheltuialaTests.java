package Tests.domainTests;

import Domain.Cheltuiala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheltuialaTests {
    private Cheltuiala cheltuiala;

    @BeforeEach
    public void setUp(){
        cheltuiala =  new Cheltuiala(1,"home", 15.0F);
    }

    @Test
    public void testConstructor(){
        assertEquals(1, cheltuiala.getId(), "id must be 1");
        assertEquals("home", cheltuiala.getNume(), "nume must be home");
        assertEquals(15.0F, cheltuiala.getSuma(), "suma must be 15.0");
    }

    @Test
    public void testSetnume(){
        cheltuiala.setNume("gym");
        assertEquals("gym", cheltuiala.getNume(), "name must be gym");
    }

    @Test
    public void testSetsuma(){
        cheltuiala.setSuma(25);
        assertEquals(25, cheltuiala.getSuma(), "suma must be 25");
    }
}
