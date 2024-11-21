package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Diagrama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheltuialaRepository extends JpaRepository<Cheltuiala , Integer> {
    //metode personalizate

    List<Cheltuiala> findByDiagrama(Diagrama diagrama);

    @Query("SELECT c.id FROM Cheltuiala c ORDER BY c.id ASC")
    List<Integer> findAllIds();
}
