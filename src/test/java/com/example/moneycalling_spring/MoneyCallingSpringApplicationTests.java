package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.*;
import com.example.moneycalling_spring.Service.*;
import com.example.moneycalling_spring.dto.*;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    private final Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);
    private final Utilizator utilizator2 = new Utilizator(1, "Marinescu", "Stefan", "parola1235", "stef.mari@email.com", dataNasterii, "M", "0723456789", profil);
    private final Diagrama diag  = new Diagrama(1, dataC, utilizator, true);
    private final Diagrama diag2 = new Diagrama(2, dataC, utilizator, true);
    private final Cheltuiala cheltuiala = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diag);
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
        assertEquals(20, profil.getDataSalar(), "profil data salariu must be 20");
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
        diag.initializeProcente(diag);

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


    // ==============================
    //        Teste Repository
    // ==============================

    private final Data dataRepo = new Data(15,10,2000);
    private final ProfilFinanciar profilRepo = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatorRepo = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",dataRepo,"mascul","0777333222", profilRepo);
    private final Diagrama diagRepo  = new Diagrama(1, dataRepo,  utilizatorRepo, true);
    private final Cheltuiala cheltuialaRepo = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagRepo);
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

        //Cautarea utilizatorului dupa email
        Optional<Utilizator> founde = utilizatorRepository.findByEmail("ion.popescu@example.com");
        assertTrue(founde.isPresent());
        assertEquals(utilizatorRepo.getPrenume(), founde.get().getPrenume());
    }


    @Test
    @Order(23)
    public void testDeletebyIdUtilizatorRepo() {
        //Initializarea cu un utilizator deja existent in repository
        Utilizator util = utilizatorRepository.getReferenceById(1);

        //Stergerea utilizatorului
        utilizatorRepository.deleteById(util.getId());

        //Cautarea utilizatorului
        assertFalse(utilizatorRepository.findById(util.getId()).isPresent());
    }

    @Test
    @Order(28)
    public void testDeleteAllUtilizatorRepo(){
        //Stergerea tuturor insantelor din repository
        utilizatorRepository.deleteAll();

        assertTrue(utilizatorRepository.findAll().isEmpty());
    }

    @Test
    @Order(14)
    public void testSaveAndFindByIdFindALlProfilFinanciar(){
        //Salvarea in repository
        profilFinanciarRepository.save(profilRepo);

        List<ProfilFinanciar> lista = profilFinanciarRepository.findAll();
        Optional<ProfilFinanciar> found = profilFinanciarRepository.findById(lista.get(0).getId());
        assertEquals(profilRepo.getVenit(), found.get().getVenit());
    }

    @Test
    @Order(24)
    public void testDeleteByIdProfilFinanciarRepo() {
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
    @Order(25)
    public void testDeleteByIdCheltuialaRepo() {
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
        assertEquals(diagRepo.getId(), found.get().getId());
        assertEquals(diagRepo.getUser().getNume(), found.get().getUser().getNume());
        assertEquals(diagRepo.isActiva(), found.get().isActiva());
    }

    @Test
    @Order(26)
    public void testDeleteByIdDiagramaRepo() {
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
        assertEquals(raportRepo.getId(), found.get().getId());
    }

    @Test
    @Order(27)
    public void testDeleteByIdRaportRepo() {
        //Initializarea cu o diagrama deja existenta in repository
        Raport raport = raportRepository.getReferenceById(1);

        //Stergerea din repository
        raportRepository.deleteById(raport.getId());

        //Cautarea raportului
        assertFalse(raportRepository.findById(raport.getId()).isPresent());
    }

    // ==============================
    //        Teste Service
    // ==============================

    private final Data dataServ = new Data(15,10,2000);
    private final ProfilFinanciar profilServ = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatorServ = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",dataServ,"mascul","0777333222", profilServ);
    private final Diagrama diagServ  = new Diagrama(1, dataServ, utilizatorServ, true);
    private final Cheltuiala cheltuialaServ = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagServ);
    private final Raport raportServ = new Raport(1, diagServ);

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

    @Test
    @Order(18)
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
    @Order(19)
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
    @Order(20)
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
        assertEquals(diagServ.getId(), diagService.findDiagramaByDataAndUser(10, 2000, 1).getId());

        //testare diag activa
        diagServ.setActiva(false);
        diagService.seteazaDiagramaActiva(diagServ);
        assertTrue(diagServ.isActiva(), "diagServ is active");

        //testare deletebyid
        diagService.stergeDiagramaById(diagServ.getId());

        List<Diagrama> lista2 = diagService.getAllDiagrame();
        assertTrue(lista2.isEmpty());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(21)
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
    @Order(22)
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
        float procent = diagServ.getProcenteCheltuieli().get(Cheltuiala.TipCheltuiala.LOCUINTA) - 5;
        assertEquals((procent * profilServ.getVenit())/100, sc);

        //testare sugerare rata by venit
        float rv = raportService.sugereazaRataByVenit(50000, 5);
        float dobanda = 0.05f;
        float sumaTotala = 50000 + 50000 * dobanda * 5;
        float numarRate = 5 * 12;
        float valoareRate = sumaTotala / numarRate;
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

        //testare deletebydiagrama


        //testare deletebyid
        raportService.stergeRaportById(raportServ.getId());

        List<Raport> lista2a = raportService.getAllRapoarteByDiagrama(diagServ);
        assertTrue(lista2a.isEmpty());
    }

    // ==============================
    //        Teste dto
    // ==============================

    private final Data datadto = new Data(15,10,2000);
    private final ProfilFinanciar profildto = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
    private final Utilizator utilizatordto = new Utilizator(1,"Ion", "Popescu","ionnuesmecher" , "ion.popescu@example.com",datadto,"mascul","0777333222", profildto);
    private final Diagrama diagdto  = new Diagrama(1, datadto, utilizatordto, true);
    private final Cheltuiala cheltuialadto = new Cheltuiala(1, "home", 50000.0F, Cheltuiala.TipCheltuiala.LOCUINTA, diagdto);
    private final Raport raportdto = new Raport(1, diagdto);


    @Order(29)
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

    @Order(30)
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

    @Order(31)
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

    @Order(32)
    @Test
    public void testDiagramaDTO(){
        DiagramaRequestDTO dreqdto = new DiagramaRequestDTO();

        //testare set get id
        dreqdto.setId(diagdto.getId());
        assertEquals(dreqdto.getId(), diagdto.getId(), "dto id must be same as diagdto id");

        //testare set get data
        dreqdto.setData(diagdto.getDataDiagrama());
        assertEquals(dreqdto.getData(), diagdto.getDataDiagrama(), "dto data must be same as diagdto data");

        //testare set det utilizator id
        dreqdto.setUserId(utilizatordto.getId());
        assertEquals(dreqdto.getUserId(), diagdto.getUser().getId(), "dto userId must be same as utilizatordto id");
    }

    @Order(33)
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

    @Order(36)
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

    @Order(34)
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

    @Order(35)
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


}
