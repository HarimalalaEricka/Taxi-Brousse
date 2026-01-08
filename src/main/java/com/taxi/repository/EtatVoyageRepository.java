package com.taxi.repository;

import com.taxi.models.EtatVoyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatVoyageRepository extends JpaRepository<EtatVoyage, Long> {
}
