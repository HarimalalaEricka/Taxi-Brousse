package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlusieurPaiementService {

    @Autowired
    private PlusieurPaiementRepository PlusieurPaiementRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private EtatPaiementRepository etatPaiementRepository;

    /**
     * Ajoute un paiement partiel/multiple à une facture, met à jour l'état de la facture selon le pourcentage payé.
     * @param factureId l'id de la facture à payer
     * @param plusieurPaiement le paiement partiel à ajouter
     * @return la facture mise à jour
     */
    public Facture payerFactureReservation(Long factureId, PlusieurPaiement plusieurPaiement) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        // Récupérer ou créer le paiement principal lié à la facture
        Paiement paiement = paiementRepository.findByFacture(facture)
                .orElseGet(() -> {
                    Paiement p = new Paiement();
                    p.setFacture(facture);
                    p.setStatus("en cours");
                    return paiementRepository.save(p);
                });

        // Lier le paiement principal au paiement partiel
        plusieurPaiement.setPaiement(paiement);
        PlusieurPaiementRepository.save(plusieurPaiement);

        // Calculer la somme totale payée
        List<PlusieurPaiement> paiements = PlusieurPaiementRepository.findByPaiement(paiement);
        java.math.BigDecimal totalPaye = paiements.stream()
                .map(PlusieurPaiement::getMontant)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        java.math.BigDecimal montantFacture = facture.getMontant();
        double pourcentage = totalPaye.divide(montantFacture, 4, java.math.RoundingMode.HALF_UP).doubleValue();

        // Mettre à jour l'état de la facture
        String nouvelEtat;
        if (pourcentage >= 1.0) {
            nouvelEtat = "paye";
        } else if (pourcentage > 0) {
            nouvelEtat = "partiellement paye";
        } else {
            nouvelEtat = "non paye";
        }
        EtatPaiement etat = etatPaiementRepository.findByEtat(nouvelEtat)
                .orElseThrow(() -> new RuntimeException("Etat de paiement non trouvé: " + nouvelEtat));
        facture.setEtatPaiement(etat);
        factureRepository.save(facture);

        // Mettre à jour le statut du paiement principal
        paiement.setStatus(nouvelEtat);
        paiementRepository.save(paiement);

        return facture;
    }

    public PlusieurPaiement create(PlusieurPaiement PlusieurPaiement) {
        return PlusieurPaiementRepository.save(PlusieurPaiement);
    }

    public List<PlusieurPaiement> getAll() {
        return PlusieurPaiementRepository.findAll();
    }

    public Optional<PlusieurPaiement> getById(Long id) {
        return PlusieurPaiementRepository.findById(id);
    }

    public PlusieurPaiement update(PlusieurPaiement PlusieurPaiement) {
        return PlusieurPaiementRepository.save(PlusieurPaiement);
    }

    public void delete(Long id) {
        PlusieurPaiementRepository.deleteById(id);
    }
}
