package com.taxi.controller;

import com.taxi.models.Societe;
import com.taxi.models.Prestation;
import com.taxi.dto.SocieteStatsDTO;
import com.taxi.service.SocieteService;
import com.taxi.service.PrestationService;
import com.taxi.service.PaiementFactureSocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/societe")
public class SocieteController {

    @Autowired
    private SocieteService societeService;

    @Autowired
    private PrestationService prestationService;

    @Autowired
    private PaiementFactureSocieteService paiementFactureSocieteService;

    @GetMapping
    public String list(Model model) {
        List<Societe> societes = societeService.getAll();
        List<SocieteStatsDTO> societeStats = new ArrayList<>();
        
        for (Societe s : societes) {
            BigDecimal montantTotal = BigDecimal.ZERO;
            BigDecimal montantPaye = BigDecimal.ZERO;
            
            List<Prestation> prestations = prestationService.getBySociete(s);
            for (Prestation p : prestations) {
                montantTotal = montantTotal.add(p.getMontantTotal());
                // Utiliser le nouveau système de paiement par facture société
                if (p.getFactureSociete() != null) {
                    montantPaye = montantPaye.add(paiementFactureSocieteService.getMontantPayePourPrestationADate(p, LocalDate.now()));
                }
            }
            
            societeStats.add(new SocieteStatsDTO(s, montantTotal, montantPaye));
        }
        
        model.addAttribute("societeStats", societeStats);
        model.addAttribute("title", "Sociétés");
        model.addAttribute("content", "Societe/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("societe", new Societe());
        model.addAttribute("title", "Nouvelle Société");
        model.addAttribute("content", "Societe/create");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Societe societe) {
        societeService.create(societe);
        return "redirect:/societe";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        societeService.delete(id);
        return "redirect:/societe";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        societeService.getById(id).ifPresent(societe -> {
            model.addAttribute("societe", societe);
        });
        model.addAttribute("title", "Modifier Société");
        model.addAttribute("content", "Societe/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Societe societe) {
        societe.setIdSociete(id);
        societeService.update(societe);
        return "redirect:/societe";
    }
}
