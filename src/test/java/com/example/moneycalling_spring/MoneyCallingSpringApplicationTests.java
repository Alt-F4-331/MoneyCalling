package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Controller.*;
import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.*;
import com.example.moneycalling_spring.Security.JwtUtil;
import com.example.moneycalling_spring.Service.*;
import com.example.moneycalling_spring.dto.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MoneyCallingSpringApplicationTests {
    // ==============================
    //        Teste Domeniu
    // ==============================


    private final Data dataNasterii = new Data(15, 12, 1998);
    private final Data dataC = new Data(9, 9, 2003);
    private final ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "Male", "0723456789", profil);
    private final Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "Male", "0723456789", profil);
    private final Diagrama diag  = new Diagrama(1, dataC, utilizator, true);
    private final Diagrama diag2 = new Diagrama(2, dataC, utilizator, true);
    private final Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diag);
    private final Raport raport = new Raport(1, diag);
    private final Abonament abonament = new Abonament(1, "iMusic", 10.0f, "Lunar", 9, 12, utilizator);


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
        assertEquals("Male", utilizator.getSex(), "user sex must be Male");
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

        //Testarea functiei set pentru lista de abonamente
        List<Abonament> listabonamente = new java.util.ArrayList<>(List.of());
        listabonamente.add(abonament);
        utilizator.setAbonamente(listabonamente);
        assertEquals(listabonamente, utilizator.getAbonamente());
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
        assertEquals(20, profil.getDataSalar(), "profil data salariu must be 20");


        //Testarea constructorului cu un id
        ProfilFinanciar pfi = new ProfilFinanciar(7);
        assertEquals(7, pfi.getId(), "pfi id must be 7");
        assertEquals(0, pfi.getVenit(), "pfi venit must be 0");
        assertEquals(".", pfi.getDomiciliu(), "pfi domiciliu must be null");
        assertEquals(0, pfi.getContainerEconomii(), "pfi container economii must be 0");
        assertEquals(1, pfi.getDataSalar(), "pfi data salariu must be 1");
    }

    @Test
    @Order(5)
    public void testConstructorGetteriData(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(15, dataNasterii.getZi(), "dataNasterii zi must be 15");
        assertEquals(12, dataNasterii.getLuna(), "dataNasterii luna must be 12");
        assertEquals(1998, dataNasterii.getAn(), "dataNasterii an must be 1998");

        assertEquals("15.12.1998", dataNasterii.toString(), "dataNasterii must be 15.12.1998");
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

        //Testarea validarii datei
        Data datatest = new Data();
        datatest.setZi(9);
        datatest.setLuna(12);
        datatest.setAn(2003);
        assertDoesNotThrow(datatest::validateDate, "datatest is valid");

        datatest.setZi(31);// month december is 31 days long
        assertDoesNotThrow(datatest::validateDate, "datatest is valid");

        datatest.setZi(32);// the month is exceeded
        assertThrows(IllegalArgumentException.class, datatest::validateDate, "december doesn't have 32 days");

        datatest.setLuna(2);
        datatest.setZi(29);
        assertThrows(IllegalArgumentException.class, datatest::validateDate, "2023 isn't leap year");

        datatest.setLuna(4);
        datatest.setZi(31);
        assertThrows(IllegalArgumentException.class, datatest::validateDate);

    }

    @Test
    @Order(7)
    public void testConstructorGetteriCheltuiala(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, cheltuiala.getId(), "cheltuiala id must be 1");
        assertEquals("home", cheltuiala.getNume(), "cheltuiala nume must be home");
        assertEquals(50000.0F, cheltuiala.getSuma(), "cheltuiala suma must be 50000.0F");
        assertEquals(Cheltuiala.TipCheltuiala.LOCUINTA, cheltuiala.getTipCheltuiala(), "cheltuiala tip must be LOCUINTA" );
        assertEquals(diag, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag content");
        assertEquals(Cheltuiala.TipCheltuiala.LOCUINTA.getProcent(), cheltuiala.getTipCheltuiala().getProcent(), "cheltuiala tip procent must be same as 30.0F");
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

        //Testarea functiei set pentru suma
        cheltuiala.setSuma(60000.0F);
        assertEquals(60000.0F, cheltuiala.getSuma(), "cheltuiala suma must be 60000.0F");

        //Testarea functiei set pentru tip cheltuiala
        cheltuiala.setTipCheltuiala(Cheltuiala.TipCheltuiala.EDUCATIE);
        assertEquals(Cheltuiala.TipCheltuiala.EDUCATIE, cheltuiala.getTipCheltuiala(), "cheltuiala tip must be EDUCATIE");

        //testare procent tip cheltuiala EDUCATIE
        Cheltuiala.TipCheltuiala.EDUCATIE.setProcent(10F);
        assertEquals(Cheltuiala.TipCheltuiala.EDUCATIE.getProcent(), cheltuiala.getTipCheltuiala().getProcent(), "cheltuiala tip procent must be 10.0F");

        //Testarea functiei set pentru diagrama
        cheltuiala.setDiagrama(diag2);
        assertEquals(diag2, cheltuiala.getDiagrama(), "cheltuiala diagrama content must be same as diag2 content");

        //Testarea functiei equals
        Cheltuiala chel = new Cheltuiala(2, "newhome", 60000.0F, Cheltuiala.TipCheltuiala.EDUCATIE, diag2);
        assertTrue(cheltuiala.equals(chel), "chel equals cheltuiala");
        chel.setSuma(55555.0f);
        assertFalse(cheltuiala.equals(chel), "chel should not equal cheltuiala");
        assertFalse(cheltuiala.equals(utilizator), "chel should not equal utilizator");

        //Testarea functiei hash
        chel = cheltuiala;
        assertEquals(cheltuiala.hashCode(), chel.hashCode(), "the hashes should be equal");
    }

    @Test
    @Order(9)
    public void testConstructorGetteriDiagrama(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, diag.getId(), "diag id must be 1");
        assertEquals(9, diag.getDataDiagrama().getZi(), "diag data zi must be 9");
        assertEquals(9, diag.getDataDiagrama().getLuna(), "diag data luna must be 9");
        assertEquals(2003, diag.getDataDiagrama().getAn() , "diag data an must be 2003");
        assertEquals(utilizator, diag.getUser(), "diag user must be utilizator");
        List<Cheltuiala> list = diag.getListaCheltuieli();
        assertEquals(list, diag.getListaCheltuieli());
        assertEquals(true, diag.isActiva(), "diag status must be true");
        Map<Cheltuiala.TipCheltuiala, Float> res = diag.getProcenteCheltuieli();
        assertEquals(null, res.get(Cheltuiala.TipCheltuiala.LOCUINTA), "diag procentcheltuiala must be 30f");
    }

    @Test
    @Order(10)
    public void testSetteriDiagrama(){

        //Testarea functiei set pentru id
        diag.setId(2);
        assertEquals(2, diag.getId(), "diag id must be 2");

        //Testarea functiei set pentru data
        dataC.setZi(29);
        dataC.setLuna(9);
        dataC.setAn(2002);
        diag.setDataDiagrama(dataC);
        assertEquals(29, diag.getDataDiagrama().getZi(), "diag data zi must be 29");
        assertEquals(9, diag.getDataDiagrama().getLuna(), "diag data luna must be 9");
        assertEquals(2002, diag.getDataDiagrama().getAn() , "diag data an must be 2002");

        //Testarea functiei set pentru utilizator
        diag.setUser(utilizator2);
        assertEquals(utilizator2, diag.getUser(), "diag user content must be same as utilizator2 content");

        //Testarea functiei set active
        diag.setActiva(false);
        assertEquals(false, diag.isActiva(), "diag status must be false");

        //Testarea functiei set pentru procente
        Map<Cheltuiala.TipCheltuiala, Float> procente = new HashMap<>();
        procente.put(Cheltuiala.TipCheltuiala.LOCUINTA, 45f);
        diag.setProcenteCheltuieli(procente);
        Map<Cheltuiala.TipCheltuiala, Float> res = diag.getProcenteCheltuieli();
        assertEquals(45f, res.get(Cheltuiala.TipCheltuiala.LOCUINTA));

        //Testarea functiei ini proc
        Map<Cheltuiala.TipCheltuiala, Float> procenteini = new HashMap<>();
        diag.setProcenteCheltuieli(procenteini);

        Cheltuiala.TipCheltuiala[] tips = Cheltuiala.TipCheltuiala.values();
        for(Cheltuiala.TipCheltuiala tip : tips){
            procenteini.put(tip, 0f);
        }
        diag.initializeProcente(diag, 6000.0f);

        for(Cheltuiala.TipCheltuiala tip :tips){
            assertNotNull(diag.getProcenteCheltuieli().get(tip), "diag procenteCheltuieli must not be null");
        }
    }

    @Test
    @Order(11)
    public void testConstructorGetteriRaport(){

        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, raport.getId(), "raport id must be 1");
        assertEquals(diag, raport.getDiagrama(), "raport diagrama content must be same as diag content");
    }

    @Test
    @Order(12)
    public void testSetteriRaport(){
        //Testarea functiei set pentru id
        raport.setId(2);
        assertEquals(2, raport.getId(), "raport id must be 2");

        //Testarea functiei set pentru diagrama
        raport.setIdDiagrama(diag2);
        assertEquals(diag2, raport.getDiagrama(), "raport diagrama content must be same as diag2 content");
    }

    @Test
    @Order(13)
    public void testConstructorGetteriAbonament(){
        //Testarea tuturor campurilor din acest obiect cu ajutorul functiilor get
        assertEquals(1, abonament.getId(), "abonamnet id must be 1");
        assertEquals("iMusic", abonament.getNume(), "abonament nume must be iMusic");
        assertEquals(10.0f, abonament.getValoare(), "abonament valoare must be 10.0f");
        assertEquals("Lunar", abonament.getTipAbonament(), "abonament tip must be Lunar");
        assertEquals(9, abonament.getZi(), "abonament zi must be 9");
        assertEquals(12, abonament.getLuna(), "abonament luna must be 12");
        assertEquals(utilizator, abonament.getUtilizator(), "abonament utilizator must be same as utilizator");
    }

    @Test
    @Order(14)
    public void testSetteriAbonament(){
        //Testarea functiei set pentru id
        abonament.setId(2);
        assertEquals(2, abonament.getId(), "abonamnet id must be 2");

        //Testarea functiei set pentru nume
        abonament.setNume("Disney+");
        assertEquals("Disney+", abonament.getNume(), "abonament nume must be Disney+");

        //Testarea functiei set pentru valoare
        abonament.setValoare(12.0f);
        assertEquals(12.0f, abonament.getValoare(), "abonament valoare must be 12.0f");

        //Testarea functiei set pentru tip abonament
        abonament.setTipAbonament("Anual");
        assertEquals("Anual", abonament.getTipAbonament(), "abonament tip must be Anual");

        //Testarea functiei set pentru zi
        abonament.setZi(23);
        assertEquals(23, abonament.getZi(), "abonament zi must be 23");

        //Testarea functiei set pentru luna
        abonament.setLuna(4);
        assertEquals(4, abonament.getLuna(), "abonament luna must be 4");

        //Testarea functiei set pentru utilizator
        abonament.setUtilizator(utilizator2);
        assertEquals(utilizator2, abonament.getUtilizator(), "abonament utilizator must be same as utilizator2");

    }


    // ==============================
    //        Teste Repository
    // ==============================

    private final Data dataRepo = new Data(15,10,2000);
    private final ProfilFinanciar profilRepo = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatorRepo = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",dataRepo,"Male","0777333222", profilRepo);
    private final Diagrama diagRepo  = new Diagrama(1, dataRepo,  utilizatorRepo, true);
    private final Cheltuiala cheltuialaRepo = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagRepo);
    private final Raport raportRepo = new Raport(1, diagRepo);
    private final Abonament abonamentRepo = new Abonament(1, "iMusic", 10.0f, "Lunar", 9, 12, utilizatorRepo);


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

    @Autowired
    private AbonamentRepository abonamentRepository;


    @Test
    @Order(15)
    public void testSaveAndFindByIdFindAllUtilizator() {
        //Cautarea utilizatorului in repository
        utilizatorRepository.save(utilizatorRepo);
        List<Utilizator> list = utilizatorRepository.findAll();
        Optional<Utilizator> found = utilizatorRepository.findById(list.get(0).getId());
        assertEquals(utilizatorRepo.getNume(), found.get().getNume());

        //Cautarea utilizatorului dupa email
        Optional<Utilizator> founde = utilizatorRepository.findByEmail("ion.popescu@example.com");
        assertTrue(founde.isPresent());
        assertEquals(utilizatorRepo.getPrenume(), founde.get().getPrenume());
    }


    @Test
    @Order(33)
    public void testDeletebyIdUtilizatorRepo() {
        //Initializarea cu un utilizator deja existent in repository
        Utilizator util = utilizatorRepository.getReferenceById(1);

        //Stergerea utilizatorului
        utilizatorRepository.deleteById(util.getId());

        //Cautarea utilizatorului
        assertFalse(utilizatorRepository.findById(util.getId()).isPresent());
    }

    @Test
    @Order(39)
    public void testDeleteAllUtilizatorRepo(){
        //Stergerea tuturor insantelor din repository
        utilizatorRepository.deleteAll();

        assertTrue(utilizatorRepository.findAll().isEmpty());
    }

    @Test
    @Order(16)
    public void testSaveAndFindByIdFindALlProfilFinanciar(){
        //Salvarea in repository
        profilFinanciarRepository.save(profilRepo);

        List<ProfilFinanciar> lista = profilFinanciarRepository.findAll();
        Optional<ProfilFinanciar> found = profilFinanciarRepository.findById(lista.get(0).getId());
        assertEquals(profilRepo.getVenit(), found.get().getVenit());
    }

    @Test
    @Order(34)
    public void testDeleteByIdProfilFinanciarRepo() {
        //Initializarea cu un profil deja existent in repository
        ProfilFinanciar profi = profilFinanciarRepository.getReferenceById(1);

        //Stergerea din repository
        profilFinanciarRepository.deleteById(profi.getId());

        //Cautarea profilului
        assertFalse(profilFinanciarRepository.findById(profi.getId()).isPresent());
    }

    @Test
    @Order(18)
    public void testSaveAndFindByIdFindAllCheltuiala(){
        //Salvare in repository
        cheltuialaRepository.save(cheltuialaRepo);

        List<Cheltuiala> lista = cheltuialaRepository.findAll();
        Optional<Cheltuiala> found = cheltuialaRepository.findById(lista.get(0).getId());
        assertEquals(cheltuialaRepo.getNume(), found.get().getNume());
    }

    @Test
    @Order(36)
    public void testDeleteByIdCheltuialaRepo() {
        //Initializarea cu o cheltuiala deja existenta in repository
        Cheltuiala chel = cheltuialaRepository.getReferenceById(1);

        //Stergerea din repository
        cheltuialaRepository.deleteById(chel.getId());

        //Cautarea cheltuielii
        assertFalse(cheltuialaRepository.findById(chel.getId()).isPresent());
    }

    @Test
    @Order(17)
    public void testSaveAndFindByIdFindAllDiagrama(){
        //Salvare in repository
        diagramaRepository.save(diagRepo);

        List<Diagrama> lista = diagramaRepository.findAll();
        Optional<Diagrama> found = diagramaRepository.findById(lista.get(0).getId());
        assertEquals(diagRepo.getId(), found.get().getId());
        assertEquals(diagRepo.getUser().getNume(), found.get().getUser().getNume());
        assertEquals(diagRepo.isActiva(), found.get().isActiva());
    }

    @Test
    @Order(35)
    public void testDeleteByIdDiagramaRepo() {
        //Initializarea cu o diagrama deja existenta in repository
        Diagrama diagr = diagramaRepository.getReferenceById(1);

        //Stergerea din repository
        diagramaRepository.deleteById(diagr.getId());

        //Cautarea diagramei
        assertFalse(diagramaRepository.findById(diagr.getId()).isPresent());
    }

    @Test
    @Order(19)
    public void testSaveAndFindByIdFindAllRaport(){
        //Salvarea in repository
        raportRepository.save(raportRepo);

        List<Raport> lista = raportRepository.findAll();
        Optional<Raport> found = raportRepository.findById(lista.get(0).getId());
        assertEquals(raportRepo.getId(), found.get().getId());
    }

    @Test
    @Order(37)
    public void testDeleteByIdRaportRepo() {
        //Initializarea cu o diagrama deja existenta in repository
        Raport raport = raportRepository.getReferenceById(1);

        //Stergerea din repository
        raportRepository.deleteById(raport.getId());

        //Cautarea raportului
        assertFalse(raportRepository.findById(raport.getId()).isPresent());
    }

    @Test
    @Order(20)
    public void testSaveAndFindByIdFindAllAbonament(){
        //Salvarea in repository
        abonamentRepository.save(abonamentRepo);

        List<Abonament> lista = abonamentRepository.findAll();
        Optional<Abonament> found = abonamentRepository.findById(lista.get(0).getId());
        assertEquals(abonamentRepo.getId(), found.get().getId());

        //testare findbyutilizator and nume
        //Optional<Abonament> found2 = abonamentRepository.findByUtilizatorAndNume(utilizatorRepo, "Ion");
        //assertEquals(abonamentRepo, found2.get());

        //testare findbytip
        List<Abonament> found3 = abonamentRepository.findByTipAbonament("Lunar");
        assertEquals(abonamentRepo.getId(), found3.get(0).getId());
    }

    @Test
    @Order(38)
    public void testDeleteByIdAbonamentRepo() {
        //Initializarea cu un abonamnet deja existent
        Abonament ab = abonamentRepository.getReferenceById(1);

        //Stergerea din repository
        abonamentRepository.deleteById(ab.getId());

        //Cautarea abonamentului
        assertFalse(abonamentRepository.findById(ab.getId()).isPresent());
    }

    // ==============================
    //        Teste Service
    // ==============================

    private final Data dataServ = new Data(15,10,2000);
    private final ProfilFinanciar profilServ = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatorServ = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",dataServ,"Male","0777333222", profilServ);
    private final Diagrama diagServ  = new Diagrama(1, dataServ, utilizatorServ, true);
    private final Cheltuiala cheltuialaServ = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagServ);
    private final Raport raportServ = new Raport(1, diagServ);
    private final Abonament abonamentServ = new Abonament(1, "iMusic", 10.0f, "Lunar", 9, 12, utilizatorServ);


    @Autowired
    UtilizatorService utilizatorService;

    @Autowired
    ProfilFinanciarService profilFinanciarService;

    @Autowired
    CheltuialaService cheltuialaService;

    @Autowired
    RaportService raportService;

    @Autowired
    DiagramaService diagService;

    @Autowired
    AbonamentService abonamentService;

    @Autowired
    AbonamentRepository abonamentRepositorySA;
    @Autowired
    DiagramaRepository diagramaRepositorySA;
    @Autowired
    UtilizatorRepository utilizatorRepositorySA;
    @Autowired
    UtilizatorService utilizatorServiceSA;
    @Autowired
    CheltuialaService cheltuialaServiceSA;

    @Test
    @Order(27)
    public void testUtilizatorService(){
        //testarea functiei deleteALL
        utilizatorService.deleteAll();
        List<Utilizator> dlist = utilizatorRepository.findAll();
        assertTrue(dlist.isEmpty());


        //Adaugarea in service a unui utilizator
        utilizatorService.saveUtilizator(utilizatorServ);
        profilFinanciarService.saveProfilFinanciar(profilServ);
        diagService.saveDiagrama(diagServ);
        cheltuialaService.saveCheltuiala(cheltuialaServ);
        raportService.saveRaport(raportServ);

        //testare getbyid
        assertEquals(utilizatorServ.getId(), utilizatorService.getById(utilizatorServ.getId()).get().getId());

        //testare firstavailable
        int res = utilizatorService.getFirstAvailableId();
        assertEquals(utilizatorServ.getId()+1 , res);

        //testare getbyemail
        assertEquals(utilizatorServ.getId(), utilizatorService.getByEmail(utilizatorServ.getEmail()).get().getId());

        //testare getall
        List<Utilizator> lista = utilizatorService.getAllUtilizatori();
        assertEquals(lista.get(0).getId(), utilizatorServ.getId());

        //testare deletebyid
        utilizatorService.stergeUtilizatorById(utilizatorServ.getId());

        List<Utilizator> lista2 = utilizatorService.getAllUtilizatori();
        assertTrue(lista2.isEmpty());

    }

    @Test
    @Order(28)
    public void testProfilFinanciarService(){
        //testarea functiei deleteALL
        profilFinanciarService.deleteAll();
        List<ProfilFinanciar> dlist = profilFinanciarRepository.findAll();
        assertTrue(dlist.isEmpty());

        //Adaugarea in service a unui profil financiar
        profilFinanciarService.saveProfilFinanciar(profilServ);

        //testare firstavailable
        int res = profilFinanciarService.getFirstAvailableId();
        assertEquals(profilServ.getId()+1, res);

        //testare getall
        List<ProfilFinanciar> lista = profilFinanciarService.getAllProfiluriFinanciare();
        assertEquals(lista.get(0).getId(), profilServ.getId());

        //testare deletebyid
        profilFinanciarService.stergeProfilFinanciarById(profilServ.getId());

        List<ProfilFinanciar> lista2 = profilFinanciarService.getAllProfiluriFinanciare();
        assertTrue(lista2.isEmpty());
    }

    @Test
    @Order(29)
    public void testDiagramaService(){
        //testarea functiei deleteALL
        diagService.deleteAll();
        List<Diagrama> dlist = diagramaRepository.findAll();
        assertTrue(dlist.isEmpty());

        //Adaugarea in service a unei diagrame
        utilizatorService.saveUtilizator(utilizatorServ);
        profilFinanciarService.saveProfilFinanciar(profilServ);
        diagService.saveDiagrama(diagServ);
        cheltuialaService.saveCheltuiala(cheltuialaServ);
        raportService.saveRaport(raportServ);

        //testare getall
        List<Diagrama> list = diagService.getAllDiagrame();
        assertEquals(list.get(0).getId(), diagServ.getId());

        //testare getallbyutilizator
        List<Diagrama> listu = diagService.getAllDiagrameByUtilizator(utilizatorServ);
        assertFalse(listu.isEmpty());

        //testare getbyid
        assertEquals(diagServ.getId(), diagService.getById(diagServ.getId()).get().getId());

        //testare getbyuser
        assertEquals(diagServ.getId(), diagService.getDiagramaActivaByUtilizator(utilizatorServ).get().getId());

        //testare findby data and user
        assertEquals(diagServ.getId(), diagService.findDiagramaByDataAndUser(10, 2000, 1).get().getId());

        //testare diag activa
        diagServ.setActiva(false);
        diagService.seteazaDiagramaActiva(diagServ);
        assertTrue(diagServ.isActiva(), "diagServ is active");

        //testare creare diag
        Diagrama newdiag = new Diagrama(99, dataServ, utilizatorServ, true);
        Diagrama test = diagService.createAndConfigureDiagrama(utilizatorServ, 15, 11, 2000);
        assertEquals(newdiag.getUser().getId(), test.getUser().getId(), "both diagrama should have the same user");
        assertEquals(newdiag.getDataDiagrama().getZi(), test.getDataDiagrama().getZi(), "zi should match");

        //testare getfirstid
        int result = diagService.getFirstAvailableId();
        assertEquals(diagServ.getId()+2, result);

        //testare bani economisti
        float banie = diagService.baniEconomisiti(diagServ, 50000);
        // banie has value 0 because of no expenes added to the diagram
        assertEquals(0.0f, banie, "banie should match");

        //testare deletebyid
        diagService.stergeDiagramaById(diagServ.getId());
        diagService.stergeDiagramaById(test.getId());

        List<Diagrama> lista2 = diagService.getAllDiagrame();
        assertTrue(lista2.isEmpty());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(30)
    public void testCheltuialaService(){
        //testarea functiei deleteALL
        cheltuialaService.deleteAll();
        List<Cheltuiala> dlist = cheltuialaRepository.findAll();
        assertTrue(dlist.isEmpty());

        //Adaugarea in service a unei cheltuieli
        utilizatorService.saveUtilizator(utilizatorServ);
        profilFinanciarService.saveProfilFinanciar(profilServ);
        diagService.saveDiagrama(diagServ);
        cheltuialaService.saveCheltuiala(cheltuialaServ);
        raportService.saveRaport(raportServ);

        //testare getall
        List<Cheltuiala> lista = cheltuialaService.getAllCheltuieli();
        assertEquals(lista.get(0).getId(), cheltuialaServ.getId());

        //testare firstavailable
        int res = cheltuialaService.getFirstAvailableId();
        assertEquals(cheltuialaServ.getId()+1 , res);

        //testare getllallbydiagrama
        List<Cheltuiala> listad = cheltuialaService.getAllCheltuieliByIdDiagrama(diagServ);
        assertFalse(listad.isEmpty());

        //testare getbyid
        assertEquals(cheltuialaServ.getId(), cheltuialaService.getById(cheltuialaServ.getId()).get().getId());

        //testare deletebyid
        cheltuialaService.stergeCheltuialaById(cheltuialaServ.getId());

        List<Cheltuiala> lista2 = cheltuialaService.getAllCheltuieli();
        assertTrue(lista2.isEmpty());

    }


    @Test
    @Order(31)
    public void testRaportService(){
        //Adaugarea in service a unui raport
        utilizatorService.saveUtilizator(utilizatorServ);
        profilFinanciarService.saveProfilFinanciar(profilServ);
        diagService.saveDiagrama(diagServ);
        cheltuialaService.saveCheltuiala(cheltuialaServ);
        raportService.saveRaport(raportServ);

        //testare getall
        List<Raport> list = raportService.getAllRapoarteByDiagrama(diagServ);
        assertEquals(list.get(0).getId(), raportServ.getId());

        //testare sugerare chirie
        Map<Cheltuiala.TipCheltuiala, Float> procente = new HashMap<>();
        procente.put(Cheltuiala.TipCheltuiala.LOCUINTA, 45f);
        diagServ.setProcenteCheltuieli(procente);
        float sc = raportService.sugereazaChirieByVenit(profilServ.getVenit(), diagServ);
        assertEquals((85 * profilServ.getVenit())/100, sc);

        //testare sugerare rata
        float rv = raportService.sugereazaRata(50000, 5);
        float dobanda = 0.05f;
        float sumaTotala = 50000 + 50000 * dobanda * 5 / 12;
        float valoareRate = sumaTotala / 5;
        valoareRate = Math.round(valoareRate*100)/100.0f;
        assertEquals(valoareRate, rv);


        //testare getbyid
        assertEquals(raportServ.getId(), raportService.getById(raportServ.getId()).get().getId());

        //testare stockare get chirie propusa
        raportService.stocheazaChiriePropusa(1, 750.0f);
        Optional<Float> res = raportService.getChiriePropusa(1);
        assertTrue(res.isPresent(), "the chiriePropusa is present");
        assertEquals(750.0f, res.get(), "the value must be the same");

        //testare elimina chirie propusa
        raportService.eliminaChiriePropusa(1);
        res = raportService.getChiriePropusa(1);
        assertFalse(res.isPresent(), "the chiriePropusa must be deleted");


        raportService.saveRaport(raportServ);
        //testare deletebyid
        raportService.stergeRaportById(raportServ.getId());

        List<Raport> lista2a = raportService.getAllRapoarteByDiagrama(diagServ);
        assertTrue(lista2a.isEmpty());
    }

    @Test
    @Order(32)
    public void testAbonamentService(){
        //Adaugarea in service a unui abonament
        utilizatorService.saveUtilizator(utilizatorServ);
        profilFinanciarService.saveProfilFinanciar(profilServ);
        diagService.saveDiagrama(diagServ);
        cheltuialaService.saveCheltuiala(cheltuialaServ);
        raportService.saveRaport(raportServ);

        //testare save
        abonamentService.addAbonament(abonamentServ);
        diagramaRepositorySA.save(diagServ);
        utilizatorRepositorySA.save(utilizatorServ);
        utilizatorServiceSA.saveUtilizator(utilizatorServ);
        cheltuialaServiceSA.saveCheltuiala(cheltuialaServ);

        abonamentService = new AbonamentService(abonamentRepositorySA, diagramaRepositorySA, utilizatorRepositorySA, cheltuialaServiceSA);

        //testare getall
        List<Abonament> lista = abonamentService.getAllAbonamente();
        assertEquals(lista.get(0).getId(), abonamentServ.getId());

        //testare first available id
        int res = abonamentService.getFirstAvailableId();
        assertEquals(abonamentServ.getId()+1 , res);

        //testare getabonament by id
        assertEquals(abonamentServ.getId(), abonamentService.getAbonamentById(abonamentServ.getId()).get().getId());

        //testare getabonament by utilizator
        assertEquals(abonamentServ.getId(), abonamentService.getAbonamenteByUtilizatorId(utilizatorServ.getId()).get(0).getId());

        //testarea get abonament by utilizator si nume
        ///assertEquals(abonamentServ.getId(), abonamentService.getAbonamentByUserAndName(utilizatorServ, utilizatorServ.getNume()).get().getId());

        //testare verificare abonamente lunar/anual
        abonamentService.verificaAbonamente();

        //testare getfirstid
        int result = abonamentService.getFirstAvailableId();
        assertEquals(abonamentServ.getId()+1, result);


        //testare eliminare abonament
        abonamentService.deleteAbonament(abonamentServ.getId());
        lista = abonamentService.getAllAbonamente();
        assertTrue(lista.isEmpty());
    }

    // ==============================
    //        Teste dto
    // ==============================

    private final Data datadto = new Data(15,10,2000);
    private final ProfilFinanciar profildto = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatordto = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",datadto,"Male","0777333222", profildto);
    private final Diagrama diagdto  = new Diagrama(1, datadto, utilizatordto, true);
    private final Cheltuiala cheltuialadto = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagdto);
    private final Raport raportdto = new Raport(1, diagdto);
    private final Abonament abonamentdto = new Abonament(1, "iMusic", 10.0f, "Lunar", 9, 12, utilizatordto);


    @Order(40)
    @Test
    public void testLoginDTO() {
        LoginRequestDTO logindto = new LoginRequestDTO();
        //testare set get email
        logindto.setEmail(utilizatordto.getEmail());
        assertEquals(logindto.getEmail(), utilizatordto.getEmail(), "dto email must be same as utilizatordto email");

        //testare set get parola
        logindto.setParola(utilizatordto.getParola());
        assertEquals(logindto.getParola(), utilizatordto.getParola(), "dto parola must be same as utilizatordto parola");
    }

    @Order(41)
    @Test
    public void testCreareContDTO(){
        CreareContDto contdto = new CreareContDto();
        CreareContDto cdto = new CreareContDto("Tudor", "Alex", "pass", "tst@email.com", datadto, "m", "00000");

        //testare set get nume
        contdto.setNume(utilizatordto.getNume());
        assertEquals(contdto.getNume(), utilizatordto.getNume(), "dto nume must be same as utilizatordto nume");
        assertEquals("Tudor", cdto.getNume(), "cdto nume must be Tudor");

        //testare set get prenume
        contdto.setPrenume(utilizatordto.getPrenume());
        assertEquals(contdto.getPrenume(), utilizatordto.getPrenume(), "dto prenume must be same as utilizatordto prenume");
        assertEquals("Alex", cdto.getPrenume(), "cdto prenume must be Alex");

        //testare set get parola
        contdto.setParola(utilizatordto.getParola());
        assertEquals(contdto.getParola(), utilizatordto.getParola(), "dto parola must be same as utilizatordto parola");
        assertEquals("pass", cdto.getParola(), "cdto parola must be pass");

        //testare set get email
        contdto.setEmail(utilizatordto.getEmail());
        assertEquals(contdto.getEmail(), utilizatordto.getEmail(), "dto email must be same as utilizatordto email");
        assertEquals("tst@email.com", cdto.getEmail(), "cdto email must be tst@email.com");

        //testare set get datanasterii
        contdto.setDataNasterii(utilizatordto.getDataNasterii());
        assertEquals(contdto.getDataNasterii(), utilizatordto.getDataNasterii(), "dto dataNasterii must be same as utilizatordto dataNasterii");
        assertEquals(datadto, cdto.getDataNasterii(), "cdto data must be same as datadto");

        //testare set get sex
        contdto.setSex(utilizatordto.getSex());
        assertEquals(contdto.getSex(), utilizatordto.getSex(), "dto sex must be same as utilizatordto sex");
        assertEquals("m", cdto.getSex(), "cdto sex must be m");

        //testare set get nrtelefon
        contdto.setNumarTelefon(utilizatordto.getNumarTelefon());
        assertEquals(contdto.getNumarTelefon(), utilizatordto.getNumarTelefon(), "dto telefon must be same as utilizatordto nrtelefon");
        assertEquals("00000", cdto.getNumarTelefon(), "cdto numar must be 00000");
    }

    @Order(42)
    @Test
    public void testDataDTO(){
        DataDTO dto = new DataDTO();
        DataDTO ddto = new DataDTO("1", "1", "2000");

        //testare set get zi
        dto.setZi("15");
        assertEquals("15", dto.getZi(), "dto zi must be 15");
        assertEquals("1", ddto.getZi(), "ddto zi must be 1");

        //testare set get luna
        dto.setLuna("10");
        assertEquals("10", dto.getLuna(), "dto luna must be 10");
        assertEquals("1", ddto.getLuna(), "ddto luna must be 1");

        //testare set get an
        dto.setAn("2000");
        assertEquals("2000", dto.getAn(), "dto an must be 2000");
        assertEquals("2000", ddto.getAn(), "ddto an must be 2000");

        Data dt = dto.toData();
        assertEquals(15, dt.getZi(), "dto zi must be 15");
        assertEquals(10, dt.getLuna(), "dto luna must be 10");
        assertEquals(2000, dt.getAn(), "dto an must be 2000");
    }

    @Order(43)
    @Test
    public void testDiagramaDTO(){
        DiagramaRequestDTO dreqdto = new DiagramaRequestDTO(1, datadto);

        //testare set get id
        dreqdto.setId(diagdto.getId());
        assertEquals(dreqdto.getId(), diagdto.getId(), "dto id must be same as diagdto id");

        //testare set get data
        dreqdto.setData(diagdto.getDataDiagrama());
        assertEquals(dreqdto.getData(), diagdto.getDataDiagrama(), "dto data must be same as diagdto data");

    }

    @Order(44)
    @Test
    public void testProfilFinanciarDTO(){
        ProfilFinanciarDto pfdto = new ProfilFinanciarDto(1600.0F, "Iasi", 13000.0F, 15);

        //testare set get venit
        assertEquals(1600.0F, pfdto.getVenit(), "dto venit must be 1600.0F");
        pfdto.setVenit(profildto.getVenit());
        assertEquals(profildto.getVenit(), pfdto.getVenit(), "dto venit must be same as profildto");

        //testare set get domiciliu
        assertEquals("Iasi", pfdto.getDomiciliu(), "dto domiciliu must be Iasi");
        pfdto.setDomiciliu(profildto.getDomiciliu());
        assertEquals(profildto.getDomiciliu(), pfdto.getDomiciliu(), "dto domiciliu must be same as profildto");

        //testare set get container
        assertEquals(13000.0F, pfdto.getContainerEconomii(), "dto domiciliu must be 13000.0F");
        pfdto.setContainerEconomii(profildto.getContainerEconomii());
        assertEquals(profildto.getContainerEconomii(), pfdto.getContainerEconomii(), "dto container must be same as profildto");

        //testare set get datasalar
        assertEquals(15, pfdto.getDataSalar(), "dto data salariu must be 15");
        pfdto.setDataSalar(profildto.getDataSalar());
        assertEquals(profildto.getDataSalar(), pfdto.getDataSalar(), "dto data salariu must be same as profildto");
    }

    @Order(45)
    @Test
    public void testCheltuialaDTO(){
        CheltuialaDTO cdto = new CheltuialaDTO("fotbal", 1000.0f, Cheltuiala.TipCheltuiala.SANATATE);

        //testare set get nume
        assertEquals("fotbal", cdto.getNume(), "dto nume must be fotbal");
        cdto.setNume(cheltuialadto.getNume());
        assertEquals(cheltuialadto.getNume(), cdto.getNume(), "dto nume must be same as cheltuialadto");

        //testare set get suma
        assertEquals(1000.0f, cdto.getSuma(), "dto suma must be 1000.0f");
        cdto.setSuma(cheltuialadto.getSuma());
        assertEquals(cheltuialadto.getSuma(), cdto.getSuma(), "dto suma must be same as cheltuialadto");

        //testare set get tip
        assertEquals(Cheltuiala.TipCheltuiala.SANATATE, cdto.getTipCheltuiala(), "dto tip must be SANATATE");
        cdto.setTipCheltuiala(cheltuialadto.getTipCheltuiala());
        assertEquals(cheltuialadto.getTipCheltuiala(), cdto.getTipCheltuiala(), "dto tip must be same as cheltuialdto");
    }

    @Test
    @Order(48)
    public void testAbonamentDTO(){
        AbonamentDTO dto = new AbonamentDTO();
        AbonamentDTO abdto = new AbonamentDTO("Netflix", 40.0f, "Anual", 10, 12);

        //testare set get nume
        assertEquals("Netflix", abdto.getNume(), "abdto nume must be ");
        dto.setNume("Spotify");
        assertEquals("Spotify", dto.getNume(), "dto nume must be Spotify");

        //testare set get valoare
        assertEquals(40.0f, abdto.getValoare(), "abdto valoare must be 40.0f");
        dto.setValoare(22.0f);
        assertEquals(22.0f, dto.getValoare(), "dto valoare must be 22.0f");

        //testare set get tip
        assertEquals("Anual", abdto.getTipAbonament(), "abdto tip abonament must be Anual");
        dto.setTipAbonament("Lunar");
        assertEquals("Lunar", dto.getTipAbonament(), "dto tip abonament must be Lunar");

        //testare set get zi
        assertEquals(10, abdto.getZiua(), "abdto zi must be 10");
        dto.setZiua(20);
        assertEquals(20, dto.getZiua(), "dto zi must be 20");

        //testare set get luna
        assertEquals(12, abdto.getLuna(), "abdto zi must be 12");
        dto.setLuna(6);
        assertEquals(6, dto.getLuna(), "dto zi must be 6");
    }

    @Order(46)
    @Test
    public void testRaportRequestDTO(){
        RaportRequestDTO rdto = new RaportRequestDTO();
        RaportRequestDTO rreqdto = new RaportRequestDTO(1, 1);

        //testare set get id
        rdto.setId(raportdto.getId());
        assertEquals(rdto.getId(), raportdto.getId(), "dto id must be same as raportdto");
        assertEquals(1, rreqdto.getId(), "rreqdto id must be 1");

        //testare set get id diagrama
        rdto.setIdDiagrama(raportdto.getDiagrama().getId());
        assertEquals(rdto.getIdDiagrama(), raportdto.getDiagrama().getId(), "dto idDiagrama must be same as raportdto");
        assertEquals(1, rreqdto.getIdDiagrama(), "rreqdto idDiagrama must be 1");

        //testare map to entity
        Raport r = RaportRequestDTO.mapToEntity(rdto, diagdto);
        assertEquals(r.getId(), rdto.getId(), "r id must be same as rdto");
        assertEquals(r.getDiagrama().getId(), diagdto.getId(), "r diag id must be same as diagdto id");

        //testare map to dto
        RaportRequestDTO dto = RaportRequestDTO.mapToDTO(r);
        assertEquals(dto.getId(), r.getId(), "dto id must be same as rdto");
        assertEquals(dto.getIdDiagrama(), r.getDiagrama().getId(), "dto id diaagrama must be same as r");
    }

    @Order(47)
    @Test
    public void testCheltuialaRequestDTO(){
        CheltuialaRequestDTO cdto = new CheltuialaRequestDTO();
        CheltuialaRequestDTO cheldto = new CheltuialaRequestDTO(1, "chirie", 300.0f, Cheltuiala.TipCheltuiala.LOCUINTA, 1);

        //testare set get nume
        cdto.setNume(cheltuialadto.getNume());
        assertEquals(cdto.getNume(), cheltuialadto.getNume(), "dto nume must be same as cheltuialadto");
        assertEquals("chirie", cheldto.getNume(), "cheldto nume must be chirie");

        //testare set get tip
        cdto.setTipCheltuiala(cheltuialadto.getTipCheltuiala());
        assertEquals(cdto.getTipCheltuiala(), cheltuialadto.getTipCheltuiala(), "dto tip cheltuiala must be same as cheltuialadto");
        assertEquals(Cheltuiala.TipCheltuiala.LOCUINTA, cheldto.getTipCheltuiala(), "cheldto tip cheltuiala must be LOCUINTA");

        //testare set get suma
        cdto.setSuma(cheltuialadto.getSuma());
        assertEquals(cdto.getSuma(), cheltuialadto.getSuma(), "dto suma must be same as cheltuialadto");
        assertEquals(300.0f, cheldto.getSuma(), "cheldto suma must be 300.0f");

        //testare map to entity
        Cheltuiala c = cdto.mapToEntity(1, diagdto);
        assertEquals(c.getNume(), cdto.getNume(), "c nume must be same as cdto");
        assertEquals(c.getTipCheltuiala(), cdto.getTipCheltuiala(), "c tip cheltuiala must be same as cdto");

        //testare map to dto
        CheltuialaRequestDTO dto = CheltuialaRequestDTO.mapToDTO(c);
        assertEquals(dto.getNume(), c.getNume(), "dto nume must be same as c");
        assertEquals(dto.getTipCheltuiala(), c.getTipCheltuiala(), "dto tip cheltuiala must be same as c");

    }

    @Test
    @Order(49)
    public void testUpdateUserDTO(){
        UpdateUserDTO updto = new UpdateUserDTO("Tudor", "Alexandru", "password", "tudor@test.com", "0742123456");

        //testare get
        assertEquals("Tudor", updto.getNume(), "dto nume must be Tudor");
        assertEquals("Alexandru", updto.getPrenume(), "dto prenume must be Alexandru");
        assertEquals("password", updto.getParola(), "dto parola must be password");
        assertEquals("tudor@test.com", updto.getEmail(), "dto email must be tudor@test.com");
        assertEquals("0742123456", updto.getNumarTelefon(), "dto numar telefon must be 0742123456");

        //testare set
        updto.setNume("newName");
        assertEquals("newName", updto.getNume(), "dto nume must be same as newName");

        updto.setPrenume("newPrenume");
        assertEquals("newPrenume", updto.getPrenume(), "dto prenume must be same as newPrenume");

        updto.setParola("newParola");
        assertEquals("newParola", updto.getParola(), "dto parola must be newParola");

        updto.setEmail("newEmail");
        assertEquals("newEmail", updto.getEmail(), "dto email must be same as newEmail");

        updto.setNumarTelefon("00000000000");
        assertEquals("00000000000", updto.getNumarTelefon(), "dto numartelefon must be same as 00000000000");

    }

    @Test
    @Order(50)
    public void testSumeResponeDTO(){
        SumeResponeDTO srdto = new SumeResponeDTO(700.0f, 50000.0f, 123000.0f);

        //testare get
        assertEquals(700.0f, srdto.getSumaCh(), "dto sumach must be 700.0f");
        assertEquals(50000.0f, srdto.getSumaContainer(), "dto sumaContainer must be 50000.0f");
        assertEquals(123000.0f, srdto.getEconomii(), "dto Economii must be 123000.0f");

        //testare set
        srdto.setSumaCh(44000.0f);
        assertEquals(44000.0f, srdto.getSumaCh(), "dto sumach must be 44000.0f");

        srdto.setSumaContainer(5100.0f);
        assertEquals(5100.0f, srdto.getSumaContainer(), "dto sumaContainer must be 5100.0f");

        srdto.setEconomii(333333.0f);
        assertEquals(333333.0f, srdto.getEconomii(), "dto Economii must be 333333.0f");
    }


    // ==============================
    //        Teste controller
    // ==============================

    private final Data datacontroller = new Data(15,10,2000);
    private final ProfilFinanciar profilcontroller = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatorcontroller = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",datacontroller,"Male","0777333222", profilcontroller);
    private final Diagrama diagcontroller  = new Diagrama(1, datacontroller, utilizatorcontroller, true);
    private final Cheltuiala cheltuialacontroller = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagcontroller);
    private final Raport raportcontroller = new Raport(1, diagcontroller);
    private final Abonament abonamentcontroller = new Abonament(1, "iMusic", 10.0f, "Lunar", 9, 12, utilizatorcontroller);

    @Autowired
    UtilizatorController utilizatorController;

    @Autowired
    ProfilFinanciarController profilController;

    @Autowired
    DiagramaController diagController;

    @Autowired
    CheltuialaController chelController;

    @Autowired
    RaportController raportController;

    @Autowired
    AbonamentController abonamentController;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    @Order(21)
    public void testUtilizatorController() {
        //data setup
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String mockToken = "mockJwtToken";

        LoginRequestDTO validLoginRequest = new LoginRequestDTO();
        validLoginRequest.setEmail(utilizatorRepo.getEmail());
        validLoginRequest.setParola(utilizatorRepo.getParola());
        LoginRequestDTO invalidLoginRequest = new LoginRequestDTO();
        invalidLoginRequest.setEmail(email);
        invalidLoginRequest.setParola(wrongPassword);

        UtilizatorController utilizatorController = new UtilizatorController(utilizatorService, profilFinanciarService, jwtUtil, cheltuialaService, diagService);

        //testarea functiei login
        ResponseEntity<String> response = utilizatorController.login(validLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be OK");

        response = utilizatorController.login(invalidLoginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(), "Status must be UNAUTHORIZED");


        //testarea functiei get utilizator by email
        ResponseEntity<Utilizator> resp_utilizator = utilizatorController.getUtilizatorByEmail(utilizatorcontroller.getEmail());
        assertEquals(HttpStatus.OK, resp_utilizator.getStatusCode(), "Status must be OK");

        //testarea functiei get utilizator by id
        resp_utilizator = utilizatorController.getUtilizatorById(utilizatorcontroller.getId());
        assertEquals(HttpStatus.OK, resp_utilizator.getStatusCode(), "Status must be OK");

        //testarea functiei de adaugare utilizator
        ProfilFinanciar profilcontroller1 = new ProfilFinanciar(99,3000.0f, "București",6000.0f , 15);
        Utilizator utilizatorcontroller1 = new Utilizator(99,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",datacontroller,"Male","0777333222", profilcontroller1);
        utilizatorRepository.save(utilizatorcontroller1);
        resp_utilizator = utilizatorController.createUtilizator(utilizatorcontroller1);
        assertEquals(HttpStatus.CREATED, resp_utilizator.getStatusCode(), "Status must be CREATED");


        //testarea functiei de stergere utilizator
        ResponseEntity<Void> resp_void = utilizatorController.deleteUtilizator(utilizatorcontroller1.getId());
        assertEquals(HttpStatus.NO_CONTENT, resp_void.getStatusCode(), "Status must be NO_CONTENT");

        //testarea functiei de returnare
        ResponseEntity<List<Utilizator>> resp_list = utilizatorController.getAllUtilizatori();
        assertEquals(HttpStatus.OK, resp_list.getStatusCode(), "Status must be OK");
    }

    @Test
    @Order(22)
    public void testProfilController() {
        //data setup
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String mockToken = "mockJwtToken";

        profilFinanciarRepository.save(profilRepo);
        utilizatorRepository.save(utilizatorRepo);
        profilFinanciarService.saveProfilFinanciar(profilRepo);
        utilizatorService.saveUtilizator(utilizatorRepo);

        LoginRequestDTO validLoginRequest = new LoginRequestDTO();
        validLoginRequest.setEmail(utilizatorRepo.getEmail());
        validLoginRequest.setParola(utilizatorRepo.getParola());

        UtilizatorController utilizatorController = new UtilizatorController(utilizatorService, profilFinanciarService, jwtUtil, cheltuialaService, diagService);
        ProfilFinanciarController profilfinanciarController = new ProfilFinanciarController(profilFinanciarService, utilizatorService, jwtUtil);


        ResponseEntity<String> response = utilizatorController.login(validLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be OK");

        //testarea functiei get all
        ResponseEntity<List<ProfilFinanciar>> resp_list = profilfinanciarController.getAllProfiluriFinanciare();
        assertEquals(HttpStatus.OK, resp_list.getStatusCode(), "Status must be OK");


        ProfilFinanciar profilcontroller1 = new ProfilFinanciar(4,3000.0f, "București",6000.0f , 15);
        profilFinanciarService.saveProfilFinanciar(profilcontroller1);
        //testarea functiei delete
        ResponseEntity<Void> resp_void = profilfinanciarController.deleteProfilFinanciarById(profilcontroller1.getId());
        assertEquals(HttpStatus.NO_CONTENT, resp_void.getStatusCode(), "Status must be NO_CONTENT");
    }

    @Test
    @Order(23)
    public void testDiagramaController(){
        //data setup
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String mockToken = "mockJwtToken";

        profilFinanciarRepository.save(profilRepo);
        utilizatorRepository.save(utilizatorRepo);

        LoginRequestDTO validLoginRequest = new LoginRequestDTO();
        validLoginRequest.setEmail(utilizatorRepo.getEmail());
        validLoginRequest.setParola(utilizatorRepo.getParola());

        UtilizatorController utilizatorController = new UtilizatorController(utilizatorService, profilFinanciarService, jwtUtil, cheltuialaService, diagService);
        ProfilFinanciarController profilfinanciarController = new ProfilFinanciarController(profilFinanciarService, utilizatorService, jwtUtil);
        DiagramaController diagramaController = new DiagramaController(diagService, utilizatorService, cheltuialaService, jwtUtil);

        ResponseEntity<String> response = utilizatorController.login(validLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be OK");

        //testarea functiei get diagrama by id
        ResponseEntity<Diagrama> resp = diagramaController.getDiagramaById(diagServ.getId());
        assertEquals(HttpStatus.OK, resp.getStatusCode(), "Status must be OK");

        //testarea functiei get all
        ResponseEntity<List<Diagrama>> resp_list = diagramaController.getAllDiagrame();
        assertEquals(HttpStatus.OK, resp_list.getStatusCode(), "Status must be OK");

        Diagrama diagcontroller1  = new Diagrama(22, datacontroller, utilizatorcontroller, true);
        //testarea functiei delete
        ResponseEntity<HttpStatus> resp_delete = diagramaController.deleteDiagrama(diagcontroller1.getId());
        assertEquals(HttpStatus.NO_CONTENT, resp_delete.getStatusCode(), "Status must be NO_CONTENT");
    }

    @Test
    @Order(24)
    public void testCheltuialaController(){
        //data setup
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String mockToken = "mockJwtToken";

        profilFinanciarRepository.save(profilRepo);
        utilizatorRepository.save(utilizatorRepo);

        LoginRequestDTO validLoginRequest = new LoginRequestDTO();
        validLoginRequest.setEmail(utilizatorRepo.getEmail());
        validLoginRequest.setParola(utilizatorRepo.getParola());

        UtilizatorController utilizatorController = new UtilizatorController(utilizatorService, profilFinanciarService, jwtUtil, cheltuialaService, diagService);
        ProfilFinanciarController profilfinanciarController = new ProfilFinanciarController(profilFinanciarService, utilizatorService, jwtUtil);
        DiagramaController diagramaController = new DiagramaController(diagService, utilizatorService, cheltuialaService, jwtUtil);
        CheltuialaController cheltuialaController = new CheltuialaController(cheltuialaService, diagService, utilizatorService, jwtUtil);

        ResponseEntity<String> response = utilizatorController.login(validLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be OK");

        //testarea functiei get cheltuiala by id
        ResponseEntity<Cheltuiala> resp = cheltuialaController.getCheltuialaById(cheltuialaServ.getId());
        assertEquals(HttpStatus.OK, resp.getStatusCode(), "Status must be OK");

        Cheltuiala cheltuialacontroller1 = new Cheltuiala(11, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagcontroller);
        //testarea functiei delete
        ResponseEntity<Void> resp_void = cheltuialaController.deleteCheltuiala(cheltuialacontroller1.getId());
        assertEquals(HttpStatus.NO_CONTENT, resp_void.getStatusCode(), "Status must be NO_CONTENT");

    }

    @Test
    @Order(25)
    public void testRaportController(){
        //data setup
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String mockToken = "mockJwtToken";

        profilFinanciarRepository.save(profilRepo);
        utilizatorRepository.save(utilizatorRepo);

        LoginRequestDTO validLoginRequest = new LoginRequestDTO();
        validLoginRequest.setEmail(utilizatorRepo.getEmail());
        validLoginRequest.setParola(utilizatorRepo.getParola());

        UtilizatorController utilizatorController = new UtilizatorController(utilizatorService, profilFinanciarService, jwtUtil, cheltuialaService, diagService);
        ProfilFinanciarController profilfinanciarController = new ProfilFinanciarController(profilFinanciarService, utilizatorService, jwtUtil);
        DiagramaController diagramaController = new DiagramaController(diagService, utilizatorService, cheltuialaService, jwtUtil);
        CheltuialaController cheltuialaController = new CheltuialaController(cheltuialaService, diagService, utilizatorService, jwtUtil);
        RaportController raportController = new RaportController(raportService, utilizatorService, diagService, cheltuialaService, jwtUtil, abonamentService);

        ResponseEntity<String> response = utilizatorController.login(validLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be OK");

        //testarea functiei save raport
        RaportRequestDTO raportrdto = new RaportRequestDTO(2, diagcontroller.getId());
        raportService.saveRaport(raportdto);
        ResponseEntity<RaportRequestDTO> resp = raportController.saveRaport(raportrdto);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode(), "Status must be CREATED");

        //testarea functiei get by id diagrama
        ResponseEntity<List<RaportRequestDTO>> resp_list = raportController.getAllRapoarteByIdDiagrama(diagcontroller.getId());
        assertEquals(HttpStatus.OK, resp_list.getStatusCode(), "Status must be OK");


        //testarea functiei delete raport
        ResponseEntity<Void> resp_void = raportController.deleteRaportById(raportrdto.getId());
        assertEquals(HttpStatus.NO_CONTENT, resp_void.getStatusCode(), "Status must be NO_CONTENT");

    }

    @Test
    @Order(26)
    public void testAbonamentController(){
        //data setup
        String email = "test@example.com";
        String wrongPassword = "wrongPassword";
        String mockToken = "mockJwtToken";

        profilFinanciarRepository.save(profilRepo);
        utilizatorRepository.save(utilizatorRepo);

        LoginRequestDTO validLoginRequest = new LoginRequestDTO();
        validLoginRequest.setEmail(utilizatorRepo.getEmail());
        validLoginRequest.setParola(utilizatorRepo.getParola());

        UtilizatorController utilizatorController = new UtilizatorController(utilizatorService, profilFinanciarService, jwtUtil, cheltuialaService, diagService);
        ProfilFinanciarController profilfinanciarController = new ProfilFinanciarController(profilFinanciarService, utilizatorService, jwtUtil);
        DiagramaController diagramaController = new DiagramaController(diagService, utilizatorService, cheltuialaService, jwtUtil);
        CheltuialaController cheltuialaController = new CheltuialaController(cheltuialaService, diagService, utilizatorService, jwtUtil);
        RaportController raportController = new RaportController(raportService, utilizatorService, diagService, cheltuialaService, jwtUtil, abonamentService);
        AbonamentController abonamentController = new AbonamentController(abonamentService, jwtUtil, utilizatorService);


        ResponseEntity<String> response = utilizatorController.login(validLoginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be OK");

        //testarea functiei delete abonament
        ResponseEntity<Void> resp_void = abonamentController.deleteAbonament(abonamentcontroller.getId());
        assertEquals(HttpStatus.NO_CONTENT, resp_void.getStatusCode(), "Status must be NO_CONTENT");
    }



    // ==============================
    //        Teste JwtUtil
    // ==============================



    @Test
    @Order(51)
    public void testJwtUtil(){

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //testarea unui token valid
        String validToken = jwtUtil.generateToken(utilizatorcontroller.getId());
        boolean isValid = jwtUtil.validateToken(validToken);
        assertTrue(isValid, "token should be valid");

        //testarea intoarcerii unui id
        int contor = jwtUtil.extractUserId(validToken);
        assertEquals(utilizatorcontroller.getId(), contor, "content should match");

        //testarea get user by token
        int id_user = jwtUtil.getUserIdByToken(validToken)-399;
        assertEquals(utilizatorcontroller.getId(), id_user, "content should match");

        //testarea unui token invalid
        String invalidToken = "invalid.token.content";
        isValid = jwtUtil.validateToken(invalidToken);
        assertFalse(isValid, "token should not be valid");


    }
}
