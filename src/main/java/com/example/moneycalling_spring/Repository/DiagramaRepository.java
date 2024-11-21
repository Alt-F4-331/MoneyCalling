package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.Diagrama;
import com.example.moneycalling_spring.Domain.Raport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagramaRepository extends JpaRepository<Diagrama, Integer> {
    //metode personalizate


}
