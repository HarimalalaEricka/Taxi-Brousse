package com.taxi.repository;

import com.taxi.models.TypePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePaiementRepository extends JpaRepository<TypePaiement, Long> {
}
