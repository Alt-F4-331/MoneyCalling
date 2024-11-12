package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator , Integer> {
    //metode personalizate

    Optional<Utilizator> findByEmail(String email);

    @Query("SELECT u.id FROM Utilizator u ORDER BY u.id ASC")
    List<Integer> findAllIds();
}
