package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Data;
import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import com.example.moneycalling_spring.Repository.ProfilFinanciarRepository;
import com.example.moneycalling_spring.Repository.UtilizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiagramaService {
    @Autowired
    private DiagramaRepository diagramarepo;

    private ProfilFinanciarRepository profilrepo;
    private UtilizatorRepository utilizatorrepo;

    @Autowired
    public DiagramaService(DiagramaRepository diagrama)
    {
        this.diagramarepo = diagrama;
    }

    public Diagrama saveDiagrama(Diagrama diagrama)
    {
        return diagramarepo.save(diagrama);
        // adauga sau actualizeaza diagrama cu id- ul dat
    }

    public void stergeDiagramaById(int id)
    {
        diagramarepo.deleteById(id);
        //sterge dupa id
    }

    public List<Diagrama> getAllDiagrame(){
        return diagramarepo.findAll();
        //returneaza toate diagramele
        // pt admin
    }
    // getalldiagrame dupa id user
    public void deleteAll(){
        diagramarepo.deleteAll();
        //sterge toate diagramele
    }

    public Optional<Diagrama> getById(int id)
    {
        return diagramarepo.findById(id);
    }


}
