package com.example.moneycalling_spring.Service;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Utilizator;
import com.example.moneycalling_spring.Exception.ResourceNotFoundException;
import com.example.moneycalling_spring.Repository.DiagramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Diagrama> diagrama1 = diagramarepo.findByDataAndUser(
                diagrama.getDataDiagrama().getLuna(),
                diagrama.getDataDiagrama().getAn(),
                diagrama.getUser().getId()
        );

        //if(diagrama1.isPresent()){
        //aici trebuie aruncata exceptie
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

    public List<Diagrama> getAllDiagrameByUtilizator(Utilizator utilizator) {
        return diagramarepo.findByUser(utilizator);
    }

    public Optional<Diagrama> getDiagramaActivaByUtilizator(Utilizator utilizator) {
        return diagramarepo.findByUserAndActiva(utilizator);
    }


    public void seteazaDiagramaActiva(Diagrama diagrama) {
        // Obține toate diagramele utilizatorului
        List<Diagrama> diagrameUtilizator = diagramarepo.findByUser(diagrama.getUser());

        // Marchează toate diagramele ca inactive
        for (Diagrama d : diagrameUtilizator) {
            d.setActiva(false);
        }

        // Setează diagrama curentă ca activă
        diagrama.setActiva(true);

        // Salvează toate modificările
        diagramarepo.saveAll(diagrameUtilizator); // Salvează diagramele inactivate
        diagramarepo.save(diagrama); // Salvează diagrama activă
    }

    public Diagrama findDiagramaByDataAndUser(int luna, int an, int userId) {
        return diagramarepo.findByDataAndUser(luna, an, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Diagrama nu a fost găsită pentru utilizatorul specificat și data: luna " + luna + ", anul " + an));
    }

}
