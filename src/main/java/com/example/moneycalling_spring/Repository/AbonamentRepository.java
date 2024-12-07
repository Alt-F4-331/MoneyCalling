package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.Abonament;
import com.example.moneycalling_spring.Domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbonamentRepository extends JpaRepository<Abonament, Integer> {
    List<Abonament> findByUtilizatorId(int utilizatorId);

    @Query("SELECT a.id FROM Abonament a ORDER BY a.id ASC")
    List<Integer> findAllIds();

    Optional<Abonament> findByUtilizatorAndNume(Utilizator utilizator, String nume);

    // Metodă care caută abonamentele de tip "Anual"
    List<Abonament> findByTipAbonament(String tipAbonament);
}
