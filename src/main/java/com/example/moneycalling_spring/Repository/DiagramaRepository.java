package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.Cheltuiala;
import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import com.example.moneycalling_spring.Domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiagramaRepository extends JpaRepository<Diagrama, Integer> {
    //metode personalizate

    List<Diagrama> findByUser(Utilizator utilizator);

    Optional<Diagrama> findByUserAndActiva(Utilizator user, boolean activa);


}
