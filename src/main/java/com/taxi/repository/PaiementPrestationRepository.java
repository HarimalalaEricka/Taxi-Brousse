package com.taxi.repository;

import com.taxi.models.PaiementPrestation;
import com.taxi.models.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaiementPrestationRepository extends JpaRepository<PaiementPrestation, Long> {

    List<PaiementPrestation> findByPrestation(Prestation prestation);

    List<PaiementPrestation> findAllByOrderByDatePaiementDesc();

    // Somme des paiements pour une prestation jusqu'à une date donnée
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementPrestation p " +
           "WHERE p.prestation = :prestation AND p.datePaiement <= :dateMax")
    BigDecimal sumMontantByPrestationAndDateMax(@Param("prestation") Prestation prestation, 
                                                  @Param("dateMax") LocalDate dateMax);

    // Somme totale des paiements pour une prestation
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementPrestation p " +
           "WHERE p.prestation = :prestation")
    BigDecimal sumMontantByPrestation(@Param("prestation") Prestation prestation);
}
