package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyCallingSpringApplicationTests {

    @Autowired
    private UtilizatorRepository utilizatorRepository;
    // ==============================
    //        Teste Domeniu
    // ==============================
    @Test
    public void testConstructorGetteriUtilizator(){
        //Configurarea obiectului Utilizator
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);

        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor definite get
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
        assertEquals(profil.getDataSalar(), utilizator.getProfil().getDataSalar(), "user data salariu profil must be 15");
    }

    @Test
    public void testSetteriUtilizator(){
        //Configurarea obiectului Utilizator
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);

        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        //Testarea functiei set pentru nume
        utilizator.setNume("newPopescu");
        assertEquals("newPopescu", utilizator.getNume(), "user nume must be newPopescu");

        //Testarea functiei set pentru prenume
        utilizator.setPrenume("newIon");
        assertEquals("newIon", utilizator.getPrenume(), "user prenume must be newIon");

        //Testarea functiei set pentru parola
        utilizator.setParola("newParola123");
        assertEquals("newParola123", utilizator.getParola(), "user parola must be newParola123");

        //Testarea functiei set pentru email
        utilizator.setEmail("newEmail@email.com");
        assertEquals("newEmail@email.com", utilizator.getEmail(), "user email must be newEmail@email.com");

        //Testarea functiei set pentru data
        Data datanew = new Data(20, 12, 2000);
        utilizator.setDataNasterii(datanew);
        assertEquals(datanew.getZi(), utilizator.getDataNasterii().getZi(), "user zi data nastere must be 20");
        assertEquals(datanew.getLuna(), utilizator.getDataNasterii().getLuna(), "user luna data nastere must be 12");
        assertEquals(datanew.getAn(), utilizator.getDataNasterii().getAn(), "user an data nastere must be 2000");

        //Testarea functiei set pentru sex
        utilizator.setSex("F");
        assertEquals("F", utilizator.getSex(), "user sex must be F");

        //Testarea functiei set pentru numar telefon
        utilizator.setNumarTelefon("newTelefon");
        assertEquals("newTelefon", utilizator.getNumarTelefon(), "user telefon must be newTelefon");

        //Testarea functiei set pentru profilul financiar asociat
        ProfilFinanciar profilFinanciarnew = new ProfilFinanciar(2, 4000.0F, "Suceava", 7000.0F, 20);
        utilizator.setProfil(profilFinanciarnew);
        assertEquals(profilFinanciarnew.getId(), utilizator.getProfil().getId(), "user id profil must be 2");
        assertEquals(profilFinanciarnew.getVenit(), utilizator.getProfil().getVenit(), "user venit profil must be 4000.0F");
        assertEquals(profilFinanciarnew.getDomiciliu(), utilizator.getProfil().getDomiciliu(), "user domiciliu profil must be Suceava");
        assertEquals(profilFinanciarnew.getContainerEconomii(), utilizator.getProfil().getContainerEconomii(), "user container economii profil must be 7000.0F");
        assertEquals(profilFinanciarnew.getDataSalar(), utilizator.getProfil().getDataSalar(), "user data salariu profil must be 20");
    }

    @Test
    public void testConstructorGetteriProfilFinanciar(){
        // Configurarea obiectului ProfilFinanciar
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, profil.getId(), "profil id must be 1");
        assertEquals(3000.0F, profil.getVenit(), "profil venit must be 3000.0F");
        assertEquals("București", profil.getDomiciliu(), "profil domiciliu must be București");
        assertEquals(6000.0F, profil.getContainerEconomii(), "profil container economii must be 6000.0F");
        assertEquals(15, profil.getDataSalar(), "profil data salariu must be 15");
    }

    @Test
    public void testSetteriProfilFinanciar(){
        //Configurarea obiectului ProfilFinanciar
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);

        //Testarea functiei set pentru id
        profil.setId(2);
        assertEquals(2, profil.getId(), "profil id must be 2");

        //Testarea functiei set pentru venit
        profil.setVenit(4444.0F);
        assertEquals(4444.0F, profil.getVenit(), "profil venit must be 4444.0F");

        //Testarea functiei set pentru domiciliu
        profil.setDomiciliu("Suceava");
        assertEquals("Suceava", profil.getDomiciliu(), "profil domiciliu must be Suceava");

        //Testarea functiei set pentru containerul de econimii
        profil.setContainerEconomii(6666.0F);
        assertEquals(6666.0F, profil.getContainerEconomii(), "profil ContainerEconomii must be 6666.0F");

        //Testarea functiei set pentru data salariu
        profil.setDataSalar(20);
        assertEquals(20, profil.getDataSalar(), "profil data salar must be 20");
    }

    @Test
    public void testConstructorGetteriData(){
        //Configurarea obiectului Data
        Data dataNasterii = new Data(15, 12, 1998);

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(15, dataNasterii.getZi(), "dataNasterii zi must be 15");
        assertEquals(12, dataNasterii.getLuna(), "dataNasterii luna must be 12");
        assertEquals(1998, dataNasterii.getAn(), "dataNasterii an must be 1998");
    }

    @Test
    public void testSetteriData(){
        //Configurarea obiectului Data
        Data dataNasterii = new Data(15, 12, 1998);

        //Testarea functiei set pentru zi nasterii
        dataNasterii.setZi(30);
        assertEquals(30, dataNasterii.getZi(), "dataNasterii zi must be 30");

        //Testarea functiei set pentru luna nasterii
        dataNasterii.setLuna(9);
        assertEquals(9, dataNasterii.getLuna(), "dataNasterii luna must be 9");

        //Testarea functiei set pentru anul nasterii
        dataNasterii.setAn(2003);
        assertEquals(2003, dataNasterii.getAn(), "dataNasterii an must be 2003");
    }

    @Test
    public void testConstructorGetteriCheltuiala(){
        //Configurarea obiectului Cheltuiala
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);

        Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, diag);

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, cheltuiala.getId(), "cheltuiala id must be 1");
        assertEquals("home", cheltuiala.getNume(), "cheltuiala nume must be home");
        assertEquals(50000.0F, cheltuiala.getSuma(), "cheltuiala suma must be 50000.0F");
        assertEquals(diag, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag content");
    }

    @Test
    public void testSetteriCheltuiala(){
        //Configurarea obiectului Cheltuiala
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);
        Diagrama diag2 = new Diagrama(2, utilizator2);

        Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, diag);

        //Testarea functiei set pentru id
        cheltuiala.setId(2);
        assertEquals(2, cheltuiala.getId(), "cheltuiala id must be 2");

        //Testarea functiei set pentru nume
        cheltuiala.setNume("newhome");
        assertEquals("newhome", cheltuiala.getNume(), "cheltuiala nume must be newhome");

        //Testarea functiei set pentru diagrama
        cheltuiala.setDiagrama(diag2);
        assertEquals(diag2, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag2 content");
    }

    @Test
    public void testConstructorGetteriDiagrama(){
        //Configurarea obiectului Diagrama
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        Diagrama diag  = new Diagrama(1, utilizator);

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, diag.getId(), "diag id must be 1");
        assertEquals(utilizator, diag.getUser(), "diag user must be utilizator");
    }

    @Test
    public void testSetteriDiagrama(){
        //Configurarea obiectului Diagrama
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);

        Diagrama diag  = new Diagrama(1, utilizator);

        //Testarea functiei set pentru id
        diag.setId(2);
        assertEquals(2, diag.getId(), "diag id must be 2");

        //Testarea functiei set pentru utilizator
        diag.setUser(utilizator2);
        assertEquals(utilizator2, diag.getUser(), "diag user content must be same as utilizator2 content");
    }

    @Test
    public void testConstructorGetteriRaport(){
        //Configurarea obiectului Raport
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);

        Raport raport = new Raport(1, diag);

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, raport.getId(), "raport id must be 1");
        assertEquals(diag, raport.getIdDiagrama(), "raport diagrama content must be same as diag content");
    }

    @Test
    public void testSetteriRaport(){
        //Configurarea obiectului Raport
        Data dataNasterii = new Data(15, 12, 1998);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
        Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);
        Diagrama diag  = new Diagrama(1, utilizator);
        Diagrama diag2 = new Diagrama(2, utilizator2);

        Raport raport = new Raport(1, diag);

        //Testarea functiei set pentru id
        raport.setId(2);
        assertEquals(2, raport.getId(), "raport id must be 2");

        //Testarea functiei set pentru diagrama
        raport.setIdDiagrama(diag2);
        assertEquals(diag2, raport.getIdDiagrama(), "raport diagrama content must be same as diag2 content");
    }



    // ==============================
    //        Teste Repository
    // ==============================



    @Test
    public void testSaveAndFindByIdUtilizator() {
        // Configurarea obiectului Utilizator
        Data dataNasterii = new Data(15, 12, 1995);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(8, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        //Salvarea in repository
        utilizatorRepository.save(utilizator);

        List<Utilizator> lista = utilizatorRepository.findAll();
        utilizator = lista.get(1);
        //Cautarea utilizatorului in repository
        Optional<Utilizator> found = utilizatorRepository.findById(utilizator.getId());
        assertTrue(found.isPresent());
        assertEquals(utilizator.getNume(), found.get().getNume());
    }

    @Test
    public void testDeletebyIdUtilizator() {
        //Initializarea cu un utilizator deja existent in repository
        Utilizator utilizator = utilizatorRepository.getReferenceById(1);

        //Stergerea utilizatorului
        utilizatorRepository.deleteById(utilizator.getId());

        //Cautarea utilizatorului
        assertFalse(utilizatorRepository.findById(utilizator.getId()).isPresent());
    }

    @Test
    public void testFindAllUtilizator(){
        //Adaugam un nou utilizator
        Data dataNasterii = new Data(12, 1, 1999);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "Iasi",6000.0f , 15);
        Utilizator utilizator = new Utilizator(2, "Ionescu", "Maria", "parola456", "maria.ionescu@email.com", dataNasterii, "F", "0734567890", profil);
        utilizatorRepository.save(utilizator);

        //Initializam lista cu toti utilizatorii din repository
        List<Utilizator> lista = utilizatorRepository.findAll();

        assertFalse(lista.isEmpty());
        assertEquals(utilizator.getNume(), lista.get(0).getNume());
    }

    @Test
    public void testFindByEmail() {
        //Configurarea obiectului Utilizator
        Data dataNasterii = new Data(1995, 12, 15);
        ProfilFinanciar profil = new ProfilFinanciar(2, 3000.0f, "București", 6000.0f, 15);
        Utilizator utilizator = new Utilizator(2, "Ionescu", "Maria", "parola456", "maria.ionescu@email.com", dataNasterii, "F", "0734567890", profil);

        //Salvarea in repository
        utilizatorRepository.save(utilizator);

        //Cautarea utilizatorului dupa email
        Optional<Utilizator> found = utilizatorRepository.findByEmail("maria.ionescu@email.com");
        assertTrue(found.isPresent());
        assertEquals(utilizator.getPrenume(), found.get().getPrenume());
    }

    @Test
    public void testDeleteAllUtilizator(){
        utilizatorRepository.deleteAll();

        assertTrue(utilizatorRepository.findAll().isEmpty());
    }
}
