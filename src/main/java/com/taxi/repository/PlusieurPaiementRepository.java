package com.taxi.repository;

import com.taxi.models.PlusieurPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taxi.models.Paiement;
import java.util.List;

@Repository
public interface PlusieurPaiementRepository extends JpaRepository<PlusieurPaiement, Long> {
	List<PlusieurPaiement> findByPaiement(Paiement paiement);
}
