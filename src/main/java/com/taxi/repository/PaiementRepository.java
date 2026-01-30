package com.taxi.repository;

import com.taxi.models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taxi.models.Facture;
import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
	Optional<Paiement> findByFacture(Facture facture);
}
