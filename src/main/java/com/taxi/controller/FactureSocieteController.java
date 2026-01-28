package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/facture-societe")
public class FactureSocieteController {

    @Autowired
    private FactureSocieteService factureSocieteService;

    @Autowired
    private PaiementFactureSocieteService paiementService;

    @Autowired
    private SocieteService societeService;

    @Autowired
    private PrestationService prestationService;

    /**
     * Liste toutes les factures société
     */
    @GetMapping
    public String list(Model model) {
        List<FactureSociete> factures = factureSocieteService.getAll();
        model.addAttribute("factures", factures);
        model.addAttribute("title", "Factures Sociétés");
        model.addAttribute("content", "FactureSociete/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    /**
     * Formulaire pour créer une facture pour une société
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("societes", societeService.getAll());
        model.addAttribute("title", "Nouvelle Facture Société");
        model.addAttribute("content", "FactureSociete/create");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    /**
     * Crée une facture pour une société avec toutes ses prestations non facturées
     */
    @PostMapping("/create")
    public String create(@RequestParam Long idSociete) {
        societeService.getById(idSociete).ifPresent(societe -> {
            factureSocieteService.createFactureForSociete(societe);
        });
        return "redirect:/facture-societe";
    }

    /**
     * Affiche les détails d'une facture
     */
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        factureSocieteService.getById(id).ifPresent(facture -> {
            model.addAttribute("facture", facture);
            model.addAttribute("paiements", paiementService.getByFacture(facture));
            
            // Calculer les montants pour chaque prestation
            List<Prestation> prestations = facture.getPrestations();
            if (prestations != null) {
                for (Prestation p : prestations) {
                    BigDecimal montantPaye = paiementService.getMontantPayePourPrestation(p);
                    BigDecimal pourcentage = paiementService.getPourcentagePayePourPrestation(p);
                    // Ces valeurs sont déjà calculées dans le modèle Prestation
                }
            }
        });
        model.addAttribute("title", "Détails Facture");
        model.addAttribute("content", "FactureSociete/details");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    /**
     * Formulaire de paiement d'une facture
     */
    @GetMapping("/payer/{id}")
    public String payerForm(@PathVariable Long id, Model model) {
        factureSocieteService.getById(id).ifPresent(facture -> {
            model.addAttribute("facture", facture);
            model.addAttribute("paiements", paiementService.getByFacture(facture));
            model.addAttribute("resteAPayer", facture.getResteAPayer());
        });
        model.addAttribute("title", "Paiement Facture");
        model.addAttribute("content", "FactureSociete/payer");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    /**
     * Effectue un paiement sur une facture
     * Le montant est automatiquement réparti proportionnellement sur toutes les prestations
     */
    @PostMapping("/payer/{id}")
    public String payer(@PathVariable Long id,
                        @RequestParam BigDecimal montant,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePaiement,
                        @RequestParam(required = false) String modePaiement) {
        factureSocieteService.getById(id).ifPresent(facture -> {
            paiementService.effectuerPaiement(facture, montant, datePaiement, modePaiement);
        });
        return "redirect:/facture-societe/" + id;
    }

    /**
     * Liste des factures par société
     */
    @GetMapping("/societe/{idSociete}")
    public String listBySociete(@PathVariable Long idSociete, Model model) {
        societeService.getById(idSociete).ifPresent(societe -> {
            List<FactureSociete> factures = factureSocieteService.getBySociete(societe);
            model.addAttribute("factures", factures);
            model.addAttribute("societe", societe);
        });
        model.addAttribute("title", "Factures par Société");
        model.addAttribute("content", "FactureSociete/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    /**
     * Historique de tous les paiements
     */
    @GetMapping("/historique")
    public String historique(Model model) {
        List<PaiementFactureSociete> paiements = paiementService.getAll();
        
        BigDecimal totalPaiements = paiements.stream()
                .map(PaiementFactureSociete::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("paiements", paiements);
        model.addAttribute("totalPaiements", totalPaiements);
        model.addAttribute("title", "Historique des Paiements");
        model.addAttribute("content", "FactureSociete/historique");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }
}
