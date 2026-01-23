package com.taxi.repository;

import com.taxi.models.Prestation;
import com.taxi.models.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation, Long> {
    List<Prestation> findBySociete(Societe societe);
    
    @Query("SELECT p FROM Prestation p WHERE MONTH(p.datePrestation) = :mois AND YEAR(p.datePrestation) = :annee")
    List<Prestation> findByMoisAndAnnee(@Param("mois") int mois, @Param("annee") int annee);
    
    @Query("SELECT p FROM Prestation p WHERE p.societe = :societe AND MONTH(p.datePrestation) = :mois AND YEAR(p.datePrestation) = :annee")
    List<Prestation> findBySocieteAndMoisAndAnnee(@Param("societe") Societe societe, @Param("mois") int mois, @Param("annee") int annee);
}
