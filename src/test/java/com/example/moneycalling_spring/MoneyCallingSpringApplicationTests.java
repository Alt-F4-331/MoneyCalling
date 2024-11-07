package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MoneyCallingSpringApplicationTests {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Test
    public void testConstructorGetteriUtilizator(){
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        assertEquals(1, utilizator.getId(), "user id must be 1");
        assertEquals("Popescu", utilizator.getNume(), "user name must be Popescu");
        assertEquals("Ion", utilizator.getPrenume(), "user prenume must be Ion");
        assertEquals("parola123", utilizator.getParola(), "user parola must be parola123");
        assertEquals("ion.popescu@email.com", utilizator.getEmail(), "user email must be ion.popescu@email.com");
        assertEquals(15, utilizator.getDataNasterii().getZi(), "user zi data nastere must be 15");
        assertEquals(12, utilizator.getDataNasterii().getLuna(), "user luna data nastere must be 12");
        assertEquals(1998, utilizator.getDataNasterii().getAn(), "user an data nastere must be 1998");
        assertEquals("M", utilizator.getSex(), "user sex must be M");
        assertEquals("0723456789", utilizator.getNumarTelefon(), "user numar must be 0723456789");
        assertEquals(profil.getId(), utilizator.getProfil().getId(), "user id profil must be 1");
        assertEquals(profil.getVenit(), utilizator.getProfil().getVenit(), "user venit profil must be 3000.0F");
        assertEquals(profil.getDomiciliu(), utilizator.getProfil().getDomiciliu(), "user domiciliu profil must be București");
        assertEquals(profil.getContainerEconomii(), utilizator.getProfil().getContainerEconomii(), "user container economii profil must be 6000.0F");
        assertEquals(profil.getDataSalar(), utilizator.getProfil().getDataSalar(), "user data salar profil must be 15");
    }

    @Test
    public void testSetteriUtilizator(){
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        utilizator.setNume("newPopescu");
        assertEquals("newPopescu", utilizator.getNume(), "user nume must be newPopescu");

        utilizator.setPrenume("newIon");
        assertEquals("newIon", utilizator.getPrenume(), "user prenume must be newIon");

        utilizator.setParola("newParola123");
        assertEquals("newParola123", utilizator.getParola(), "user parola must be newParola123");

        utilizator.setEmail("newEmail@email.com");
        assertEquals("newEmail@email.com", utilizator.getEmail(), "user email must be newEmail@email.com");

        Data datanew = new Data(20, 12, 2000);
        utilizator.setDataNasterii(datanew);
        assertEquals(datanew.getZi(), utilizator.getDataNasterii().getZi(), "user zi data nastere must be 20");
        assertEquals(datanew.getLuna(), utilizator.getDataNasterii().getLuna(), "user luna data nastere must be 12");
        assertEquals(datanew.getAn(), utilizator.getDataNasterii().getAn(), "user an data nastere must be 2000");

        utilizator.setSex("F");
        assertEquals("F", utilizator.getSex(), "user sex must be F");

        utilizator.setNumarTelefon("newTelefon");
        assertEquals("newTelefon", utilizator.getNumarTelefon(), "user telefon must be newTelefon");

        ProfilFinanciar profilFinanciarnew = new ProfilFinanciar(2, 4000.0F, "Suceava", 7000.0F, 20);
        utilizator.setProfil(profilFinanciarnew);
        assertEquals(profilFinanciarnew.getId(), utilizator.getProfil().getId(), "user id profil must be 2");
        assertEquals(profilFinanciarnew.getVenit(), utilizator.getProfil().getVenit(), "user venit profil must be 4000.0F");
        assertEquals(profilFinanciarnew.getDomiciliu(), utilizator.getProfil().getDomiciliu(), "user domiciliu profil must be Suceava");
        assertEquals(profilFinanciarnew.getContainerEconomii(), utilizator.getProfil().getContainerEconomii(), "user container economii profil must be 7000.0F");
        assertEquals(profilFinanciarnew.getDataSalar(), utilizator.getProfil().getDataSalar(), "user data salar profil must be 20");
    }

    @Test
    public void testConstructorGetteriProfilFinanciar(){
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);

        assertEquals(1, profil.getId(), "profil id must be 1");
        assertEquals(3000.0F, profil.getVenit(), "profil venit must be 3000.0F");
        assertEquals("București", profil.getDomiciliu(), "profil domiciliu must be București");
        assertEquals(6000.0F, profil.getContainerEconomii(), "profil data salar must be 6000.0F");
        assertEquals(15, profil.getDataSalar(), "profil data salar must be 15");
    }

    @Test
    public void testSetteriProfilFinanciar(){
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);

        profil.setId(2);
        assertEquals(2, profil.getId(), "profil id must be 2");

        profil.setVenit(4444.0F);
        assertEquals(4444.0F, profil.getVenit(), "profil venit must be 4444.0F");

        profil.setDomiciliu("Suceava");
        assertEquals("Suceava", profil.getDomiciliu(), "profil domiciliu must be Suceava");

        profil.setContainerEconomii(6666.0F);
        assertEquals(6666.0F, profil.getContainerEconomii(), "profil ContainerEconomii must be 6666.0F");

        profil.setDataSalar(20);
        assertEquals(20, profil.getDataSalar(), "profil data salar must be 20");
    }

    @Test
    public void testConstructorGetteriData(){
        Data dataNasterii = new Data(15, 12, 1998);

        assertEquals(15, dataNasterii.getZi(), "dataNasterii zi must be 15");
        assertEquals(12, dataNasterii.getLuna(), "dataNasterii luna must be 12");
        assertEquals(1998, dataNasterii.getAn(), "dataNasterii an must be 1998");
    }

    @Test
    public void testSetteriData(){
        Data dataNasterii = new Data(15, 12, 1998);

        dataNasterii.setZi(30);
        assertEquals(30, dataNasterii.getZi(), "dataNasterii zi must be 30");

        dataNasterii.setLuna(9);
        assertEquals(9, dataNasterii.getLuna(), "dataNasterii luna must be 9");

        dataNasterii.setAn(2003);
        assertEquals(2003, dataNasterii.getAn(), "dataNasterii an must be 2003");
    }

    @Test
    public void testConstructorGetteriCheltuiala(){
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);
        Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, diag);

        assertEquals(1, cheltuiala.getId(), "cheltuiala id must be 1");
        assertEquals("home", cheltuiala.getNume(), "cheltuiala nume must be home");
        assertEquals(50000.0F, cheltuiala.getSuma(), "cheltuiala suma must be 50000.0F");
        assertEquals(diag, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag content");
    }

    @Test
    public void testSetteriCheltuiala(){
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);
        Diagrama diag2 = new Diagrama(2, utilizator2);
        Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, diag);

        cheltuiala.setId(2);
        assertEquals(2, cheltuiala.getId(), "cheltuiala id must be 2");

        cheltuiala.setNume("newhome");
        assertEquals("newhome", cheltuiala.getNume(), "cheltuiala nume must be newhome");

        cheltuiala.setDiagrama(diag2);
        assertEquals(diag2, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag2 content");
    }

    @Test
    public void testConstructorGetteriDiagrama(){
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);

        assertEquals(1, diag.getId(), "diag id must be 1");
        assertEquals(utilizator, diag.getUser(), "diag user must be utilizator");
    }

    @Test
    public void testSetteriDiagrama(){
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);

        diag.setId(2);
        assertEquals(2, diag.getId(), "diag id must be 2");

        diag.setUser(utilizator2);
        assertEquals(utilizator2, diag.getUser(), "diag user content must be same as utilizator2 content");

    }



    /*@Test
    public void testSaveAndFindByIdUtilizator() {
        // Configurarea obiectului Utilizator
        Data dataNasterii = new Data(15, 12, 1995);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        // Salvarea utilizatorului
        utilizatorRepository.save(utilizator);

        // Căutarea utilizatorului salvat
        Optional<Utilizator> found = utilizatorRepository.findById(utilizator.getId());
        assertTrue(found.isPresent());
        assertEquals(utilizator.getNume(), found.get().getNume());
    }

    @Test
    public void testFindByEmail() {
        // Configurarea și salvarea unui utilizator nou
        Data dataNasterii = new Data(1995, 12, 15);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(2, "Ionescu", "Maria", "parola456", "maria.ionescu@email.com", dataNasterii, "F", "0734567890", profil);
        utilizatorRepository.save(utilizator);

        // Căutarea utilizatorului după email
        Optional<Utilizator> found = utilizatorRepository.findByEmail("maria.ionescu@email.com");
        assertTrue(found.isPresent());
        assertEquals(utilizator.getPrenume(), found.get().getPrenume());
    }
     */
}
