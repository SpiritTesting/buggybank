package com.spirittesting.academy.repository;

import com.spirittesting.academy.domain.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface KundeRepository extends JpaRepository<Kunde, String> {

    Set<Kunde> findAllByNameStartingWith(String name);

}
