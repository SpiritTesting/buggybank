package com.spirittesting.academy.repository;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Zahlung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ZahlungRepository extends JpaRepository<Zahlung, UUID> {

    List<Zahlung> findAllByZiel_Kontonummer(String kontonummer);

    List<Zahlung> findAllByQuelle_Kontonummer(String kontonummer);

}
