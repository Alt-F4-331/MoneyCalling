package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilFinanciarRepository extends JpaRepository<ProfilFinanciar, Integer> {
    //metode avansate
}
