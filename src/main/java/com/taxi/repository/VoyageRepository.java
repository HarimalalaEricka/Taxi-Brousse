package com.taxi.repository;

import com.taxi.models.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    List<Voyage> findByEtatVoyage_EtatAndTrajet_IdTrajet(String etat, Long idTrajet);
}
