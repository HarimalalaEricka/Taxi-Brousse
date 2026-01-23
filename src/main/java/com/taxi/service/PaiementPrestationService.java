package com.taxi.service;

import com.taxi.models.PaiementPrestation;
import com.taxi.models.Prestation;
import com.taxi.repository.PaiementPrestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaiementPrestationService {

    @Autowired
    private PaiementPrestationRepository paiementPrestationRepository;

    public PaiementPrestation create(PaiementPrestation paiement) {
        return paiementPrestationRepository.save(paiement);
    }

    public List<PaiementPrestation> getAll() {
        return paiementPrestationRepository.findAllByOrderByDatePaiementDesc();
    }

    public List<PaiementPrestation> getByPrestation(Prestation prestation) {
        return paiementPrestationRepository.findByPrestation(prestation);
    }

    // Montant payé total pour une prestation
    public BigDecimal getMontantPaye(Prestation prestation) {
        return paiementPrestationRepository.sumMontantByPrestation(prestation);
    }

    // Montant payé jusqu'à une date donnée
    public BigDecimal getMontantPayeADate(Prestation prestation, LocalDate dateMax) {
        return paiementPrestationRepository.sumMontantByPrestationAndDateMax(prestation, dateMax);
    }

    // Reste à payer à une date donnée
    public BigDecimal getResteAPayerADate(Prestation prestation, LocalDate dateMax) {
        BigDecimal total = prestation.getMontantTotal();
        BigDecimal paye = getMontantPayeADate(prestation, dateMax);
        return total.subtract(paye);
    }
}
