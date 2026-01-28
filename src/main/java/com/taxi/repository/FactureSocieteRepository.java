package com.taxi.repository;

import com.taxi.models.FactureSociete;
import com.taxi.models.Societe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactureSocieteRepository extends JpaRepository<FactureSociete, Long> {
    
    // Trouver toutes les factures d'une société
    List<FactureSociete> findBySociete(Societe societe);
    
    // Trouver toutes les factures ordonnées par date
    List<FactureSociete> findAllByOrderByDateFactureDesc();
    
    // Trouver les factures d'une société ordonnées par date
    List<FactureSociete> findBySocieteOrderByDateFactureDesc(Societe societe);
    
    // Trouver par numéro de facture
    Optional<FactureSociete> findByNumFacture(String numFacture);
    
    // Trouver les factures non payées (état paiement = 'non paye' ou 'partiel')
    @Query("SELECT f FROM FactureSociete f WHERE f.etatPaiement.etat IN ('non paye', 'partiel')")
    List<FactureSociete> findFacturesNonPayees();
    
    // Trouver les factures non payées d'une société
    @Query("SELECT f FROM FactureSociete f WHERE f.societe = :societe AND f.etatPaiement.etat IN ('non paye', 'partiel')")
    List<FactureSociete> findFacturesNonPayeesBySociete(@Param("societe") Societe societe);
}
