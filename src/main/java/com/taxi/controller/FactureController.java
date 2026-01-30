package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.repository.*;
import com.taxi.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.taxi.models.Reservation;
import com.taxi.service.ReservationService;
import com.taxi.service.PrixBilletService;
import com.taxi.service.TypePaiementService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/api/Facture") // à adapter pour chaque entité, ex: /api/voyages
public class FactureController {

    @Autowired
    private FactureService FactureService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PrixBilletService prixBilletService;

    @Autowired
    private TypePaiementService typePaiementService;

    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private PlusieurPaiementRepository plusieurPaiementRepository;

    // Créer une entité
    @PostMapping
    public Facture create(@RequestBody Facture Facture) {
        return FactureService.create(Facture);
    }

    // Lire toutes les entités
    @GetMapping("/list")
    public String getAll(Model model) {
        List<Facture> factures = FactureService.getAll();
        Map<Long, Reservation> reservationParFacture = new HashMap<>();
        
        for (Facture facture : factures) {
            // Récupérer la réservation associée à cette facture
            Reservation reservation = reservationService.getByFactureId(facture.getIdFacture())
                    .orElse(null); // retourne null si pas de réservation
            
            if (reservation != null) {
                reservationParFacture.put(facture.getIdFacture(), reservation);
            }
        }
        
        model.addAttribute("factures", factures);
        model.addAttribute("reservationParFacture", reservationParFacture);
        model.addAttribute("title", "Factures");
        model.addAttribute("content", "Facture/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }
@GetMapping("/{id}")
public String getById(@PathVariable Long id, Model model) {
    try {
        // Récupérer la facture
        Facture facture = FactureService.getById(id)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        
        // Récupérer la réservation associée à cette facture
        Reservation reservation = reservationService.getByFactureId(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée pour cette facture"));
        
        // Debug: Vérifier la date de réservation
        System.out.println("Date réservation: " + reservation.getDateReservation());
        System.out.println("Type de date: " + (reservation.getDateReservation() != null ? 
            reservation.getDateReservation().getClass().getName() : "null"));
        
        // Calculer les détails de prix pour cette réservation
        Map<String, Object> detailsPrix = reservationService.calculDetailsPrixParReservation(reservation);
        
        // Paiement principal
        Paiement paiement = paiementRepository.findByFacture(facture).orElse(null);
        java.util.List<PlusieurPaiement> paiements = paiement != null ? plusieurPaiementRepository.findByPaiement(paiement) : java.util.Collections.emptyList();
        java.math.BigDecimal totalPaye = paiements.stream().map(PlusieurPaiement::getMontant).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        java.math.BigDecimal totalReservation = detailsPrix.get("totalReservation") instanceof java.math.BigDecimal ? (java.math.BigDecimal) detailsPrix.get("totalReservation") : new java.math.BigDecimal(detailsPrix.get("totalReservation").toString());
        java.math.BigDecimal resteAPayer = totalReservation.subtract(totalPaye);

        // Ajouter les informations au modèle
        model.addAttribute("facture", facture);
        model.addAttribute("reservation", reservation);
        model.addAttribute("detailsPrix", detailsPrix);
        model.addAttribute("detailsLignes", detailsPrix.get("detailsLignes"));
        model.addAttribute("voyage", reservation.getVoyage());
        model.addAttribute("trajet", reservation.getVoyage().getTrajet());
        model.addAttribute("utilisateur", facture.getUtilisateur());
        model.addAttribute("typesPaiement", typePaiementService.getAll());
        model.addAttribute("paiements", paiements);
        model.addAttribute("resteAPayer", resteAPayer);
        model.addAttribute("totalPaye", totalPaye);
        model.addAttribute("title", "Détails Facture");
        model.addAttribute("content", "Facture/factureDetails");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
        
    } catch (Exception e) {
        model.addAttribute("error", "Erreur: " + e.getMessage());
        e.printStackTrace(); // Pour voir l'erreur complète dans les logs
        return "error";
    }
}
    // Mettre à jour un Facture
    @PutMapping("/{id}")
    public Facture update(@PathVariable Long id, @RequestBody Facture updatedFacture) {
        updatedFacture.setIdFacture(id);
        return FactureService.update(updatedFacture);
    }

    // Supprimer un Facture
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        FactureService.delete(id);
    }
}
