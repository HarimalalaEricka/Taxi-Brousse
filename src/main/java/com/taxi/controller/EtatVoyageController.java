package com.taxi.controller;

import com.taxi.models.EtatVoyage;
import com.taxi.service.EtatVoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/EtatVoyage") // à adapter pour chaque entité, ex: /api/voyages
public class EtatVoyageController {

    @Autowired
    private EtatVoyageService EtatVoyageService;

    // Créer une entité
    @PostMapping
    public EtatVoyage create(@RequestBody EtatVoyage EtatVoyage) {
        return EtatVoyageService.create(EtatVoyage);
    }

    // Lire toutes les entités
    @GetMapping
    public List<EtatVoyage> getAll() {
        return EtatVoyageService.getAll();
    }

   // Lire une entité par id
    @GetMapping("/{id}")
    public EtatVoyage getById(@PathVariable Long id) {
        return EtatVoyageService.getById(id).orElse(null);
    }

    // Mettre à jour un EtatVoyage
    @PutMapping("/{id}")
    public EtatVoyage update(@PathVariable Long id, @RequestBody EtatVoyage updatedEtatVoyage) {
        updatedEtatVoyage.setIdEtatVoyage(id);
        return EtatVoyageService.update(updatedEtatVoyage);
    }

    // Supprimer un EtatVoyage
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        EtatVoyageService.delete(id);
    }
}
