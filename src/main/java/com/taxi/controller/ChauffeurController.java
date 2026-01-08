package com.taxi.controller;

import com.taxi.models.Chauffeur;
import com.taxi.service.ChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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
    @GetMapping
    public List<Chauffeur> getAll() {
        return ChauffeurService.getAll();
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
}
