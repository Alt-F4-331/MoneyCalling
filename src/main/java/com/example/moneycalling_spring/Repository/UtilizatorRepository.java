package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilizatorRepository extends JpaRepository<Utilizator , Integer> {
    //metode personalizate
}
