package com.taxi.service;

import com.taxi.models.Prestation;
import com.taxi.models.Societe;
import com.taxi.repository.PrestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PrestationService {

    @Autowired
    private PrestationRepository prestationRepository;

    public Prestation create(Prestation prestation) {
        return prestationRepository.save(prestation);
    }

    public List<Prestation> getAll() {
        return prestationRepository.findAll();
    }

    public Optional<Prestation> getById(Long id) {
        return prestationRepository.findById(id);
    }

    public Prestation update(Prestation prestation) {
        return prestationRepository.save(prestation);
    }

    public void delete(Long id) {
        prestationRepository.deleteById(id);
    }

    public List<Prestation> getBySociete(Societe societe) {
        return prestationRepository.findBySociete(societe);
    }

    public List<Prestation> getByMoisAnnee(int mois, int annee) {
        return prestationRepository.findByMoisAndAnnee(mois, annee);
    }

    public BigDecimal calculerCA(int mois, int annee) {
        List<Prestation> prestations = prestationRepository.findByMoisAndAnnee(mois, annee);
        BigDecimal total = BigDecimal.ZERO;
        for (Prestation p : prestations) {
            BigDecimal montant = p.getTarifPrestation().getPrixUnitaire()
                    .multiply(BigDecimal.valueOf(p.getQuantite()));
            total = total.add(montant);
        }
        return total;
    }

    public BigDecimal calculerCABySociete(Societe societe, int mois, int annee) {
        List<Prestation> prestations = prestationRepository.findBySocieteAndMoisAndAnnee(societe, mois, annee);
        BigDecimal total = BigDecimal.ZERO;
        for (Prestation p : prestations) {
            BigDecimal montant = p.getTarifPrestation().getPrixUnitaire()
                    .multiply(BigDecimal.valueOf(p.getQuantite()));
            total = total.add(montant);
        }
        return total;
    }
}
