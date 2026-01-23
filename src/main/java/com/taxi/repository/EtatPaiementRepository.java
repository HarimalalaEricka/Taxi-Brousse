package com.taxi.repository;

import com.taxi.models.EtatPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatPaiementRepository extends JpaRepository<EtatPaiement, Long> {
    Optional<EtatPaiement> findByEtat(String etat);
}
