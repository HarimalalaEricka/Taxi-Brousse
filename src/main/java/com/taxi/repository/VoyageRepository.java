package com.taxi.repository;

import com.taxi.models.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    List<Voyage> findByEtatVoyage_EtatAndTrajet_IdTrajet(String etat, Long idTrajet);
    
    @Query("SELECT v FROM Voyage v WHERE MONTH(v.dateDepart) = :mois AND YEAR(v.dateDepart) = :annee")
    List<Voyage> findByMoisAndAnnee(@Param("mois") int mois, @Param("annee") int annee);
}
