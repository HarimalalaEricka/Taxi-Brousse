package com.taxi.controller;

import com.taxi.models.Trajet;
import com.taxi.service.TrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Controller
@RequestMapping("/api/Trajet") // à adapter pour chaque entité, ex: /api/voyages
public class TrajetController {

    @Autowired
    private TrajetService TrajetService;

    // Créer une entité
    @PostMapping
    public Trajet create(@RequestBody Trajet Trajet) {
        return TrajetService.create(Trajet);
    }

    // Lire toutes les entités
    @GetMapping("/list")
    public String getAll(Model model) {
        List<Trajet> trajets = TrajetService.getAll();
        model.addAttribute("trajets", trajets);
        model.addAttribute("title", "Trajet");
        model.addAttribute("content", "Trajet/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

   // Lire une entité par id
    @GetMapping("/{id}")
    public Trajet getById(@PathVariable Long id) {
        return TrajetService.getById(id).orElse(null);
    }

    // Mettre à jour un Trajet
    @PutMapping("/{id}")
    public Trajet update(@PathVariable Long id, @RequestBody Trajet updatedTrajet) {
        updatedTrajet.setIdTrajet(id);
        return TrajetService.update(updatedTrajet);
    }

    // Supprimer un Trajet
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        TrajetService.delete(id);
    }
    
    @GetMapping("/createTrajet")
    public String createTrajetForm( Model model)
    {
        model.addAttribute("title", "Trajet");
        model.addAttribute("content", "Trajet/create_trajet");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }
    @PostMapping("/createTrajet")
    public String createTrajetSubmit(@RequestParam String villeDepart,
                                     @RequestParam String villeArrivee,
                                     @RequestParam BigDecimal distance,
                                     @RequestParam Integer duree,
                                     Model model) {
        Trajet trajet = new Trajet();
        trajet.setVilleDepart(villeDepart);
        trajet.setVilleArrivee(villeArrivee);
        trajet.setDistance(distance);
        trajet.setDureeEstimee(duree);

        TrajetService.create(trajet);
        return "redirect:/api/Trajet/list";
    }
}
