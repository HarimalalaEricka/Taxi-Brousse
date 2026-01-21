package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/PrixBillet")
public class PrixBilletController {

    @Autowired
    private PrixBilletService prixBilletService;
    
    @Autowired
    private TrajetService trajetService;
    
    @Autowired
    private TypePlaceService typePlaceService;

    @GetMapping("/list")
    public String getAll(Model model) {
        List<PrixBillet> prixBillets = prixBilletService.getAll();
        model.addAttribute("prixBillets", prixBillets);
        model.addAttribute("title", "PrixBillet");
        model.addAttribute("content", "PrixBillet/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Optional<PrixBillet> prixBillet = prixBilletService.getById(id);
        if (prixBillet.isPresent()) {
            model.addAttribute("prixBillet", prixBillet.get());
            return "PrixBillet/details";
        }
        return "redirect:/api/PrixBillet/list";
    }

    @GetMapping("/createPrixBillet")
    public String createPrixBilletForm(Model model) {
        List<Trajet> trajets = trajetService.getAll();
        List<TypePlace> types = typePlaceService.getAll();
        model.addAttribute("trajets", trajets);
        model.addAttribute("types", types);
         model.addAttribute("title", "PrixBillet");
        model.addAttribute("content", "PrixBillet/create_prixBillet");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/createPrixBillet")
    public String createPrixBilletSubmit(@RequestParam LocalDate dateDebut,
                                         @RequestParam BigDecimal prix,
                                         @RequestParam Long trajetId,
                                         @RequestParam Long typePlaceId,
                                         Model model) {
        
        try {
            // Récupérer le trajet et le type de place
            Trajet trajet = trajetService.getById(trajetId)
                    .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));
            
            TypePlace typePlace = typePlaceService.getById(typePlaceId)
                    .orElseThrow(() -> new RuntimeException("Type de place non trouvé"));
            
            // 1. Mettre à jour la date de fin du dernier prix pour le même trajet et type
            PrixBillet lastPrix = prixBilletService.getLastPrixBilletByTrajetAndType(trajetId, typePlaceId);
            
            if (lastPrix != null) {
                // Si le dernier prix a une date de fin null ou après la nouvelle date de début
                if (lastPrix.getDateFin() == null || lastPrix.getDateFin().isAfter(dateDebut)) {
                    // Mettre la date de fin à la veille de la nouvelle date de début
                    lastPrix.setDateFin(dateDebut.minusDays(1));
                    prixBilletService.update(lastPrix);
                }
            }
            
            // 2. Créer le nouveau prix
            PrixBillet prixBillet = new PrixBillet();
            prixBillet.setDateDebut(dateDebut);
            prixBillet.setPrix(prix);
            prixBillet.setTrajet(trajet);
            prixBillet.setTypePlace(typePlace);
            // La date de fin reste null pour le nouveau prix (actuellement valide)
            
            prixBilletService.create(prixBillet);
            
            return "redirect:/api/PrixBillet/list";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur : " + e.getMessage());
            List<Trajet> trajets = trajetService.getAll();
            List<TypePlace> types = typePlaceService.getAll();
            model.addAttribute("trajets", trajets);
            model.addAttribute("types", types);
            return "PrixBillet/create_prixBillet";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePrixBillet(@PathVariable Long id,
                                   @RequestParam LocalDate dateFin,
                                   Model model) {
        try {
            PrixBillet prixBillet = prixBilletService.getById(id)
                    .orElseThrow(() -> new RuntimeException("Prix non trouvé"));
            
            prixBillet.setDateFin(dateFin);
            prixBilletService.update(prixBillet);
            
            return "redirect:/api/PrixBillet/list";
            
        } catch (Exception e) {
            model.addAttribute("error", "Erreur : " + e.getMessage());
            return "redirect:/api/PrixBillet/list";
        }
    }
}