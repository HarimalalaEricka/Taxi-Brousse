package com.taxi.repository;

import com.taxi.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

@Repository
public interface PrixBilletRepository extends JpaRepository<PrixBillet, Long> {
    List<PrixBillet> findByTrajetIdTrajet(Long idTrajet);
    List<PrixBillet> findByTrajetAndDateFinIsNull(Trajet trajet);
    
    @Query("SELECT p.prix FROM PrixBillet p WHERE p.trajet.id = :trajetId AND p.typePlace.id = :typePlaceId")
    double findLastPrixByTrajetIdTrajetAndTypePlace(@Param("trajetId") Long trajetId, 
                                                   @Param("typePlaceId") Long typePlaceId);

    @Query("SELECT p FROM PrixBillet p WHERE p.trajet.idTrajet = :trajetId " +
           "AND p.typePlace.idTypePlace = :typePlaceId " +
           "ORDER BY p.dateDebut DESC")
    Optional<PrixBillet> findTopByTrajetIdTrajetAndTypePlaceIdTypePlaceOrderByDateDebutDesc(
            @Param("trajetId") Long trajetId, 
            @Param("typePlaceId") Long typePlaceId);

}
