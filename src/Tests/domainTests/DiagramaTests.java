package Tests.domainTests;

import Domain.Cheltuiala;
import Domain.Diagrama;
import org.junit.jupiter.api.BeforeEach;

public class DiagramaTests {
    private Diagrama diagrama;
    private Cheltuiala cheltuiala1;
    private Cheltuiala cheltuiala2;
    private Cheltuiala cheltuiala3;

    @BeforeEach
    public void setUp() {
        cheltuiala1 = new Cheltuiala(1, "food", 70);
        cheltuiala2 = new Cheltuiala(2, "transport", 40);
        //cheltuiala3 = new Cheltuiala(3, "", 50);
        //diagrama = new Diagrama();
    }
}
