package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.PaiementFactureSocieteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaiementFactureSocieteService {

    @Autowired
    private PaiementFactureSocieteRepository paiementRepository;

    @Autowired
    private FactureSocieteService factureSocieteService;

    /**
     * Effectue un paiement sur une facture société
     * Le montant sera automatiquement réparti proportionnellement sur toutes les prestations
     */
    @Transactional
    public PaiementFactureSociete effectuerPaiement(FactureSociete facture, BigDecimal montant, 
                                                     LocalDate datePaiement, String modePaiement) {
        PaiementFactureSociete paiement = new PaiementFactureSociete(facture, montant, datePaiement);
        paiement.setModePaiement(modePaiement);
        paiement.setReference("PAY-" + System.currentTimeMillis());
        
        paiement = paiementRepository.save(paiement);
        
        // Mettre à jour l'état de paiement de la facture
        factureSocieteService.updateEtatPaiement(facture);
        
        return paiement;
    }

    /**
     * Effectue un paiement sur une facture société (version simplifiée)
     */
    @Transactional
    public PaiementFactureSociete effectuerPaiement(FactureSociete facture, BigDecimal montant, LocalDate datePaiement) {
        return effectuerPaiement(facture, montant, datePaiement, null);
    }

    public List<PaiementFactureSociete> getAll() {
        return paiementRepository.findAllByOrderByDatePaiementDesc();
    }

    public List<PaiementFactureSociete> getByFacture(FactureSociete facture) {
        return paiementRepository.findByFactureSociete(facture);
    }

    public BigDecimal getMontantPayeFacture(FactureSociete facture) {
        return paiementRepository.sumMontantByFacture(facture);
    }

    public BigDecimal getMontantPayeFactureADate(FactureSociete facture, LocalDate dateMax) {
        return paiementRepository.sumMontantByFactureAndDateMax(facture, dateMax);
    }

    /**
     * Calcule le montant payé pour une prestation spécifique basé sur les paiements de sa facture
     * Utilise la répartition proportionnelle
     */
    public BigDecimal getMontantPayePourPrestation(Prestation prestation) {
        FactureSociete facture = prestation.getFactureSociete();
        if (facture == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalFacture = facture.getMontantTotal();
        if (totalFacture.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalPaiements = getMontantPayeFacture(facture);
        BigDecimal montantPrestation = prestation.getMontantTotal();
        
        // Montant payé = (total_paiements / total_facture) * montant_prestation
        return totalPaiements.multiply(montantPrestation)
                .divide(totalFacture, 2, java.math.RoundingMode.HALF_UP);
    }

    /**
     * Calcule le montant payé pour une prestation jusqu'à une date donnée
     */
    public BigDecimal getMontantPayePourPrestationADate(Prestation prestation, LocalDate dateMax) {
        FactureSociete facture = prestation.getFactureSociete();
        if (facture == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalFacture = facture.getMontantTotal();
        if (totalFacture.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalPaiementsADate = getMontantPayeFactureADate(facture, dateMax);
        BigDecimal montantPrestation = prestation.getMontantTotal();
        
        return totalPaiementsADate.multiply(montantPrestation)
                .divide(totalFacture, 2, java.math.RoundingMode.HALF_UP);
    }

    /**
     * Calcule le pourcentage payé pour une prestation
     */
    public BigDecimal getPourcentagePayePourPrestation(Prestation prestation) {
        FactureSociete facture = prestation.getFactureSociete();
        if (facture == null) {
            return BigDecimal.ZERO;
        }
        return facture.getPourcentagePaye();
    }

    public List<PaiementFactureSociete> getPaiementsEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        return paiementRepository.findByDateBetween(dateDebut, dateFin);
    }
}
