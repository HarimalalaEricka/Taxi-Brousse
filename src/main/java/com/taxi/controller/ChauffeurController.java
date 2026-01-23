package com.taxi.controller;

import com.taxi.models.*;
import com.taxi.service.ChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/Chauffeur") // à adapter pour chaque entité, ex: /api/voyages
public class ChauffeurController {

    @Autowired
    private ChauffeurService ChauffeurService;

    // Créer une entité
    @PostMapping
    public Chauffeur create(@RequestBody Chauffeur Chauffeur) {
        return ChauffeurService.create(Chauffeur);
    }

    // Lire toutes les entités
    @GetMapping("/list")
    public String getAll(Model model) {
        List<Chauffeur> chauffeurs = ChauffeurService.getAll();
        model.addAttribute("chauffeurs", chauffeurs);
        model.addAttribute("title", "Chauffeur");
        model.addAttribute("content", "Chauffeur/list");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "reservation-list.css");
        return "layout";
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Chauffeur getById(@PathVariable Long id) {
        return ChauffeurService.getById(id).orElse(null);
    }

    // Mettre à jour un Chauffeur
    @PutMapping("/{id}")
    public Chauffeur update(@PathVariable Long id, @RequestBody Chauffeur updatedChauffeur) {
        updatedChauffeur.setIdChauffeur(id);
        return ChauffeurService.update(updatedChauffeur);
    }

    // Supprimer un Chauffeur
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ChauffeurService.delete(id);
    }

    @GetMapping("/createChauffeur")
    public String createChauffeurForm( Model model)
    {
        model.addAttribute("title", "Chauffeur");
        model.addAttribute("content", "Chauffeur/create_chauffeur");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }
    @PostMapping("/createChauffeur")
    public String createChauffeurSubmit(@RequestParam String nom,
                                     @RequestParam String prenom,
                                     @RequestParam String telephone,
                                     Model model) {
        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setNom(nom);
        chauffeur.setPrenom(prenom);
        chauffeur.setTelephone(telephone);
        chauffeur.setStatus(StatutUtilisateur.ACTIF);

        ChauffeurService.create(chauffeur);
        return "redirect:/api/Chauffeur/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteChauffeur(@PathVariable Long id) {
        ChauffeurService.delete(id);
        return "redirect:/api/Chauffeur/list";
    }

    @GetMapping("/edit/{id}")
    public String editChauffeurForm(@PathVariable Long id, Model model) {
        ChauffeurService.getById(id).ifPresent(chauffeur -> {
            model.addAttribute("chauffeur", chauffeur);
        });
        model.addAttribute("title", "Modifier Chauffeur");
        model.addAttribute("content", "Chauffeur/edit");
        model.addAttribute("fragment", "content");
        model.addAttribute("pageCss", "input.css");
        return "layout";
    }

    @PostMapping("/edit/{id}")
    public String updateChauffeur(@PathVariable Long id,
                                  @RequestParam String nom,
                                  @RequestParam String prenom,
                                  @RequestParam String telephone) {
        ChauffeurService.getById(id).ifPresent(chauffeur -> {
            chauffeur.setNom(nom);
            chauffeur.setPrenom(prenom);
            chauffeur.setTelephone(telephone);
            ChauffeurService.update(chauffeur);
        });
        return "redirect:/api/Chauffeur/list";
    }

    @GetMapping("/desactiver/{id}")
    public String desactiverChauffeur(@PathVariable Long id) {
        ChauffeurService.getById(id).ifPresent(chauffeur -> {
            chauffeur.setStatus(StatutUtilisateur.INACTIF);
            ChauffeurService.update(chauffeur);
        });
        return "redirect:/api/Chauffeur/list";
    }

    @GetMapping("/activer/{id}")
    public String activerChauffeur(@PathVariable Long id) {
        ChauffeurService.getById(id).ifPresent(chauffeur -> {
            chauffeur.setStatus(StatutUtilisateur.ACTIF);
            ChauffeurService.update(chauffeur);
        });
        return "redirect:/api/Chauffeur/list";
    }
}
