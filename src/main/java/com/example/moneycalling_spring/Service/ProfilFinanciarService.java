package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Repository.ProfilFinanciarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilFinanciarService {
    @Autowired
    private ProfilFinanciarRepository profilrepo;

    @Autowired
    public ProfilFinanciarService(ProfilFinanciarRepository pf)
    {
        this.profilrepo = pf;
    }

    public ProfilFinanciar saveProfilFinanciar(ProfilFinanciar pf)
    {
        return profilrepo.save(pf);
        // creeaza un nou obiect de tip profil financiar
        // daca id-ul nu exista, iar daca exista
        // modifica obiectul cu id-ul dat
    }

    public void stergeProfilFinanciarById(int id)
    {
        profilrepo.deleteById(id);
        // sterge un profil dupa id
    }

    public List<ProfilFinanciar> getAllProfiluriFinanciare()
    {
        return profilrepo.findAll();
        // returneaza toate profilurile financiare
        // doar pt administrator?
    }

    public void deleteAll(){
        profilrepo.deleteAll();
        // sterge toate profilurile financiare
    }

    //metode extra dupa
}
