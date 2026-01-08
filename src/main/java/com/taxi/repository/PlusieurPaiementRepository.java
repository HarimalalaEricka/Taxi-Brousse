package com.taxi.repository;

import com.taxi.models.PlusieurPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlusieurPaiementRepository extends JpaRepository<PlusieurPaiement, Long> {
}
