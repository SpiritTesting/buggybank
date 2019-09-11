package com.spirittesting.academy.repository;

import com.spirittesting.academy.domain.Konto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface KontoRepository extends JpaRepository<Konto, String> {

    Set<Konto> findAllByKunde_Kundennummer(String kundennummer);

}
