package com.taxi.service;

import com.taxi.models.*;
import com.taxi.repository.FactureSocieteRepository;
import com.taxi.repository.EtatPaiementRepository;
import com.taxi.repository.PrestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FactureSocieteService {

    @Autowired
    private FactureSocieteRepository factureSocieteRepository;

    @Autowired
    private PrestationRepository prestationRepository;

    @Autowired
    private EtatPaiementRepository etatPaiementRepository;

    /**
     * Crée une nouvelle facture pour une société et y associe toutes ses prestations non facturées
     * Les prestations déjà associées à une autre facture (clôturée ou non) ne sont pas affectées
     */
    @Transactional
    public FactureSociete createFactureForSociete(Societe societe) {
        // Vérifier s'il y a des prestations non facturées
        List<Prestation> prestationsNonFacturees = getPrestationsNonFacturees(societe);
        
        if (prestationsNonFacturees.isEmpty()) {
            return null; // Pas de prestations à facturer
        }
        
        FactureSociete facture = new FactureSociete(societe, LocalDate.now());
        
        // État initial: non payé
        etatPaiementRepository.findByEtat("non paye").ifPresent(facture::setEtatPaiement);
        
        facture = factureSocieteRepository.save(facture);
        
        // Associer toutes les prestations non facturées de cette société
        for (Prestation p : prestationsNonFacturees) {
            p.setFactureSociete(facture);
            prestationRepository.save(p);
        }
        
        return facture;
    }

    /**
     * Récupère toutes les prestations non facturées d'une société
     */
    public List<Prestation> getPrestationsNonFacturees(Societe societe) {
        List<Prestation> prestations = prestationRepository.findBySociete(societe);
        return prestations.stream()
                .filter(p -> p.getFactureSociete() == null)
                .toList();
    }

    /**
     * Vérifie si une société a des prestations non facturées
     */
    public boolean hasPrestationsNonFacturees(Societe societe) {
        return !getPrestationsNonFacturees(societe).isEmpty();
    }

    /**
     * Vérifie si une facture est clôturée (entièrement payée)
     */
    public boolean isFactureCloturee(FactureSociete facture) {
        return facture.getResteAPayer().compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * Crée une facture pour une société avec des prestations spécifiques
     */
    @Transactional
    public FactureSociete createFactureWithPrestations(Societe societe, List<Long> prestationIds) {
        FactureSociete facture = new FactureSociete(societe, LocalDate.now());
        etatPaiementRepository.findByEtat("non paye").ifPresent(facture::setEtatPaiement);
        final FactureSociete savedFacture = factureSocieteRepository.save(facture);
        
        for (Long prestationId : prestationIds) {
            prestationRepository.findById(prestationId).ifPresent(p -> {
                // Ne pas associer si déjà facturée
                if (p.getFactureSociete() == null) {
                    p.setFactureSociete(savedFacture);
                    prestationRepository.save(p);
                }
            });
        }
        
        return savedFacture;
    }

    /**
     * Clôture manuellement une facture (marque comme payée même si pas totalement payée)
     */
    @Transactional
    public void cloturerFacture(FactureSociete facture) {
        etatPaiementRepository.findByEtat("paye").ifPresent(facture::setEtatPaiement);
        factureSocieteRepository.save(facture);
    }

    public List<FactureSociete> getAll() {
        return factureSocieteRepository.findAllByOrderByDateFactureDesc();
    }

    public Optional<FactureSociete> getById(Long id) {
        return factureSocieteRepository.findById(id);
    }

    public List<FactureSociete> getBySociete(Societe societe) {
        return factureSocieteRepository.findBySocieteOrderByDateFactureDesc(societe);
    }

    public List<FactureSociete> getFacturesNonPayees() {
        return factureSocieteRepository.findFacturesNonPayees();
    }

    public List<FactureSociete> getFacturesNonPayeesBySociete(Societe societe) {
        return factureSocieteRepository.findFacturesNonPayeesBySociete(societe);
    }

    /**
     * Récupère les factures clôturées (entièrement payées) d'une société
     */
    public List<FactureSociete> getFacturesCloturees(Societe societe) {
        return getBySociete(societe).stream()
                .filter(this::isFactureCloturee)
                .toList();
    }

    /**
     * Met à jour l'état de paiement de la facture en fonction du montant payé
     */
    @Transactional
    public void updateEtatPaiement(FactureSociete facture) {
        BigDecimal total = facture.getMontantTotal();
        BigDecimal paye = facture.getMontantPaye();
        
        String etat;
        if (paye.compareTo(BigDecimal.ZERO) == 0) {
            etat = "non paye";
        } else if (paye.compareTo(total) >= 0) {
            etat = "paye";
        } else {
            etat = "partiel";
        }
        
        etatPaiementRepository.findByEtat(etat).ifPresent(facture::setEtatPaiement);
        factureSocieteRepository.save(facture);
    }

    public FactureSociete save(FactureSociete facture) {
        return factureSocieteRepository.save(facture);
    }

    public void delete(Long id) {
        factureSocieteRepository.deleteById(id);
    }
}
