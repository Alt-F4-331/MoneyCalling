package com.example.moneycalling_spring;

import com.example.moneycalling_spring.Domain.*;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MoneyCallingSpringApplicationTests {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Test
    public void testSaveAndFindById() {
        // Configurarea obiectului Utilizator
        Data dataNasterii = new Data(1995, 12, 15);
        ProfilFinanciar profil = new ProfilFinanciar(1,3000.0f, "București",6000.0f , 15);
        Utilizator utilizator = new Utilizator(1, "Popescu", "Ion", "parola123", "ion.popescu@email.com", dataNasterii, "M", "0723456789", profil);

        // Salvarea utilizatorului
        utilizatorRepository.save(utilizator);

        // Căutarea utilizatorului salvat
        Optional<Utilizator> found = utilizatorRepository.findById(utilizator.getId());
        assertTrue(found.isPresent());
        assertEquals(utilizator.getNume(), found.get().getNume());
    }

    /*@Test
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
