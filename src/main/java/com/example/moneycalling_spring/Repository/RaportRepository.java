
package com.example.moneycalling_spring.Repository;


import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Domain.Utilizator;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaportRepository extends JpaRepository<Raport, Integer>{

    List<Raport> findByDiagrama(Diagrama diagrama);


    @Modifying
    @Transactional
    @Query("DELETE FROM Raport r WHERE r.diagrama.id = :idDiagrama")
    void deleteAllByDiagrama(@Param("idDiagrama") Diagrama idDiagrama);



    //metode extra pe viitor
}