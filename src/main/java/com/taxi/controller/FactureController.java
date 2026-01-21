package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.taxi.models.Reservation;
import com.taxi.service.ReservationService;
import com.taxi.service.PrixBilletService;
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
        return "Facture/list";
    }

// @GetMapping("/{id}")
// public String getById(@PathVariable Long id, Model model) {
//     try {
//         // Récupérer la facture
//         Facture facture = FactureService.getById(id)
//                 .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        
//         // Récupérer les réservations
//         List<Reservation> reservations = reservationService.getByFactureId(id);
        
//         if (reservations.isEmpty()) {
//             throw new RuntimeException("Aucune réservation trouvée");
//         }
        
//         // Récupérer le trajet et les prix
//         Long trajetId = reservations.get(0).getVoyage().getTrajet().getIdTrajet();
//         List<PrixBillet> prixBillets = prixBilletService.getPrixByTrajetId(trajetId);
        
//         // Prendre le premier prix comme référence
//         BigDecimal prixUnitaire = !prixBillets.isEmpty() ? prixBillets.get(0).getPrix() : BigDecimal.ZERO;
        
//         // Calculer le montant
//         BigDecimal montantTotal = prixUnitaire.multiply(new BigDecimal(reservations.size()));
//         facture.setMontant(montantTotal);
        
//         // Ajouter au modèle
//         model.addAttribute("facture", facture);
//         model.addAttribute("reservations", reservations);
//         model.addAttribute("nombrePlaces", reservations.size());
//         model.addAttribute("prixUnitaire", prixUnitaire);
//         model.addAttribute("prixBillets", prixBillets);
        
//         return "Facture/factureDetails";
        
//     } catch (Exception e) {
//         model.addAttribute("error", "Erreur: " + e.getMessage());
//         return "error";
//     }
// }
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
        
        // Ajouter les informations au modèle
        model.addAttribute("facture", facture);
        model.addAttribute("reservation", reservation);
        model.addAttribute("detailsPrix", detailsPrix);
        model.addAttribute("detailsLignes", detailsPrix.get("detailsLignes"));
        model.addAttribute("voyage", reservation.getVoyage());
        model.addAttribute("trajet", reservation.getVoyage().getTrajet());
        model.addAttribute("utilisateur", facture.getUtilisateur());
        
        return "Facture/factureDetails";
        
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
