package com.taxi.repository;

import com.taxi.models.EtatVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EtatVehiculeRepository extends JpaRepository<EtatVehicule, Long> {
    Optional<EtatVehicule> findByStatus(String status);
}
