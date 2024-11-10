/*package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyCallingSpringApplicationTests {
    // ==============================
    //        Teste Domeniu
    // ==============================


    private final Data dataNasterii = new Data(15, 12, 1998);
    private final ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
    private final Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);
    private final Diagrama diag  = new Diagrama(1, utilizator);
    private final Diagrama diag2 = new Diagrama(2, utilizator2);
    private final Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, diag);
    private final Raport raport = new Raport(1, diag);

    @Test
    @Order(1)
    public void testConstructorGetteriUtilizator(){
        //Configurarea obiectului Utilizator

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
    @Order(2)
    public void testSetteriUtilizator(){
        //Configurarea obiectului Utilizator

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
    @Order(3)
    public void testConstructorGetteriProfilFinanciar(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, profil.getId(), "profil id must be 1");
        assertEquals(3000.0F, profil.getVenit(), "profil venit must be 3000.0F");
        assertEquals("București", profil.getDomiciliu(), "profil domiciliu must be București");
        assertEquals(6000.0F, profil.getContainerEconomii(), "profil container economii must be 6000.0F");
        assertEquals(15, profil.getDataSalar(), "profil data salariu must be 15");
    }

    @Test
    @Order(4)
    public void testSetteriProfilFinanciar(){

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
    @Order(5)
    public void testConstructorGetteriData(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(15, dataNasterii.getZi(), "dataNasterii zi must be 15");
        assertEquals(12, dataNasterii.getLuna(), "dataNasterii luna must be 12");
        assertEquals(1998, dataNasterii.getAn(), "dataNasterii an must be 1998");
    }

    @Test
    @Order(6)
    public void testSetteriData(){

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
    @Order(7)
    public void testConstructorGetteriCheltuiala(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, cheltuiala.getId(), "cheltuiala id must be 1");
        assertEquals("home", cheltuiala.getNume(), "cheltuiala nume must be home");
        assertEquals(50000.0F, cheltuiala.getSuma(), "cheltuiala suma must be 50000.0F");
        assertEquals(diag, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag content");
    }

    @Test
    @Order(8)
    public void testSetteriCheltuiala(){

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
    @Order(9)
    public void testConstructorGetteriDiagrama(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, diag.getId(), "diag id must be 1");
        assertEquals(utilizator, diag.getUser(), "diag user must be utilizator");
    }

    @Test
    @Order(10)
    public void testSetteriDiagrama(){

        //Testarea functiei set pentru id
        diag.setId(2);
        assertEquals(2, diag.getId(), "diag id must be 2");

        //Testarea functiei set pentru utilizator
        diag.setUser(utilizator2);
        assertEquals(utilizator2, diag.getUser(), "diag user content must be same as utilizator2 content");
    }

    @Test
    @Order(11)
    public void testConstructorGetteriRaport(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, raport.getId(), "raport id must be 1");
        assertEquals(diag, raport.getIdDiagrama(), "raport diagrama content must be same as diag content");
    }

    @Test
    @Order(12)
    public void testSetteriRaport(){
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

    private final Data dataRepo = new Data(15,10,2000);
    private final ProfilFinanciar profilRepo = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatorRepo = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",dataRepo,"mascul","0777333222", profilRepo);
    private final Diagrama diagRepo  = new Diagrama(1, utilizatorRepo);
    private final Cheltuiala cheltuialaRepo = new Cheltuiala(1, "home", 50000.0F, diagRepo);
    private final Raport raportRepo = new Raport(1, diagRepo);

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private ProfilFinanciarRepository profilFinanciarRepository;

    @Autowired
    private CheltuialaRepository cheltuialaRepository;

    @Autowired
    private DiagramaRepository diagramaRepository;

    @Autowired
    private RaportRepository raportRepository;


    @Test
    @Order(13)
    public void testSaveAndFindByIdFindAllUtilizator() {
        //Cautarea utilizatorului in repository
        List<Utilizator> list = utilizatorRepository.findAll();
        Optional<Utilizator> found = utilizatorRepository.findById(list.get(0).getId());
        assertEquals(utilizatorRepo.getNume(), found.get().getNume());
    }
/*
    @Test
    public void testFindByEmail() {

        //Cautarea utilizatorului dupa email
        Optional<Utilizator> found = utilizatorRepository.findByEmail("ion.popescu@example.com");
        assertTrue(found.isPresent());
        assertEquals(utilizator.getPrenume(), found.get().getPrenume());
    }

*/
   /* @Test
    @Order(18)
    public void testDeletebyIdUtilizator() {
        //Initializarea cu un utilizator deja existent in repository
        Utilizator util = utilizatorRepository.getReferenceById(1);

        //Stergerea utilizatorului
        utilizatorRepository.deleteById(util.getId());

        //Cautarea utilizatorului
        assertFalse(utilizatorRepository.findById(util.getId()).isPresent());
    }
/*
    @Test
    public void testDeleteAllUtilizator(){
        //Stergerea tuturor insantelor din repository
        utilizatorRepository.deleteAll();

        assertTrue(utilizatorRepository.findAll().isEmpty());
    }
*/
   /* @Test
    @Order(14)
    public void testSaveAndFindByIdFindALlProfilFinanciar(){
        //Salvarea in repository
        profilFinanciarRepository.save(profilRepo);

        List<ProfilFinanciar> lista = profilFinanciarRepository.findAll();
        Optional<ProfilFinanciar> found = profilFinanciarRepository.findById(lista.get(0).getId());
        assertEquals(profilRepo.getVenit(), found.get().getVenit());
    }

    @Test
    @Order(19)
    public void testDeleteByIdProfilFinanciar() {
        //Initializarea cu un profil deja existent in repository
        ProfilFinanciar profi = profilFinanciarRepository.getReferenceById(1);

        //Stergerea din repository
        profilFinanciarRepository.deleteById(profi.getId());

        //Cautarea profilului
        assertFalse(profilFinanciarRepository.findById(profi.getId()).isPresent());
    }

    @Test
    @Order(16)
    public void testSaveAndFindByIdFindAllCheltuiala(){
        //Salvare in repository
        cheltuialaRepository.save(cheltuialaRepo);

        List<Cheltuiala> lista = cheltuialaRepository.findAll();
        Optional<Cheltuiala> found = cheltuialaRepository.findById(lista.get(0).getId());
        assertEquals(cheltuialaRepo.getNume(), found.get().getNume());
    }

    @Test
    @Order(20)
    public void testDeleteByIdCheltuiala() {
        //Initializarea cu o cheltuiala deja existenta in repository
        Cheltuiala chel = cheltuialaRepository.getReferenceById(1);

        //Stergerea din repository
        cheltuialaRepository.deleteById(chel.getId());

        //Cautarea cheltuielii
        assertFalse(cheltuialaRepository.findById(chel.getId()).isPresent());
    }

    @Test
    @Order(15)
    public void testSaveAndFindByIdFindAllDiagrama(){
        //Salvare in repository
        diagramaRepository.save(diagRepo);

        List<Diagrama> lista = diagramaRepository.findAll();
        Optional<Diagrama> found = diagramaRepository.findById(lista.get(0).getId());
        assertEquals(diagRepo.getUser(), found.get().getUser());
    }

    @Test
    @Order(21)
    public void testDeleteByIdDiagrama() {
        //Initializarea cu o diagrama deja existenta in repository
        Diagrama diagr = diagramaRepository.getReferenceById(1);

        //Stergerea din repository
        diagramaRepository.deleteById(diagr.getId());

        //Cautarea diagramei
        assertFalse(diagramaRepository.findById(diagr.getId()).isPresent());
    }

    @Test
    @Order(17)
    public void testSaveAndFindByIdFindAllRaport(){
        //Salvarea in repository
        raportRepository.save(raportRepo);

        List<Raport> lista = raportRepository.findAll();
        Optional<Raport> found = raportRepository.findById(lista.get(0).getId());
        assertEquals(raportRepo.getIdDiagrama(), found.get().getIdDiagrama());
    }

    @Test
    @Order(22)
    public void testDeleteByIdRaport() {
        //Initializarea cu o diagrama deja existenta in repository
        Raport raport = raportRepository.getReferenceById(1);

        //Stergerea din repository
        raportRepository.deleteById(raport.getId());

        //Cautarea raportului
        assertFalse(raportRepository.findById(raport.getId()).isPresent());
    }

    // ==============================
    //        Teste Repository
    // ==============================

}*/
