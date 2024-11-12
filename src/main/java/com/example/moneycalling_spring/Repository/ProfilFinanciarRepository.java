package com.example.moneycalling_spring.Repository;

import com.example.moneycalling_spring.Domain.ProfilFinanciar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilFinanciarRepository extends JpaRepository<ProfilFinanciar, Integer> {


    @Query("SELECT u.id FROM ProfilFinanciar u ORDER BY u.id ASC")
    List<Integer> findAllIds();
    //metode avansate
}
