package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagramaService {
    @Autowired
    private DiagramaRepository diagramarepo;

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
    }

    public void deleteAll(){
        diagramarepo.deleteAll();
        //sterge toate diagramele
    }
}
