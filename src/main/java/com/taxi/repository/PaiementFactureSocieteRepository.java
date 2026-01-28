package com.taxi.repository;

import com.taxi.models.FactureSociete;
import com.taxi.models.PaiementFactureSociete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaiementFactureSocieteRepository extends JpaRepository<PaiementFactureSociete, Long> {
    
    // Trouver tous les paiements d'une facture
    List<PaiementFactureSociete> findByFactureSociete(FactureSociete factureSociete);
    
    // Trouver tous les paiements ordonnés par date
    List<PaiementFactureSociete> findAllByOrderByDatePaiementDesc();
    
    // Somme des paiements pour une facture
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementFactureSociete p WHERE p.factureSociete = :facture")
    BigDecimal sumMontantByFacture(@Param("facture") FactureSociete facture);
    
    // Somme des paiements pour une facture jusqu'à une date
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementFactureSociete p " +
           "WHERE p.factureSociete = :facture AND p.datePaiement <= :dateMax")
    BigDecimal sumMontantByFactureAndDateMax(@Param("facture") FactureSociete facture, 
                                              @Param("dateMax") LocalDate dateMax);
    
    // Paiements entre deux dates
    @Query("SELECT p FROM PaiementFactureSociete p WHERE p.datePaiement BETWEEN :dateDebut AND :dateFin ORDER BY p.datePaiement DESC")
    List<PaiementFactureSociete> findByDateBetween(@Param("dateDebut") LocalDate dateDebut, 
                                                    @Param("dateFin") LocalDate dateFin);
}
