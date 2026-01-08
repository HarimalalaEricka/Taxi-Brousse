package com.taxi.controller;

import com.taxi.models.PlusieurPaiement;
import com.taxi.service.PlusieurPaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/PlusieurPaiement") // à adapter pour chaque entité, ex: /api/voyages
public class PlusieurPaiementController {

    @Autowired
    private PlusieurPaiementService PlusieurPaiementService;

    // Créer une entité
    @PostMapping
    public PlusieurPaiement create(@RequestBody PlusieurPaiement PlusieurPaiement) {
        return PlusieurPaiementService.create(PlusieurPaiement);
    }

    // Lire toutes les entités
    @GetMapping
    public List<PlusieurPaiement> getAll() {
        return PlusieurPaiementService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public PlusieurPaiement getById(@PathVariable Long id) {
        return PlusieurPaiementService.getById(id).orElse(null);
    }

    // Mettre à jour un PlusieurPaiement
    @PutMapping("/{id}")
    public PlusieurPaiement update(@PathVariable Long id, @RequestBody PlusieurPaiement updatedPlusieurPaiement) {
        updatedPlusieurPaiement.setIdPlusieurPaiement(id);
        return PlusieurPaiementService.update(updatedPlusieurPaiement);
    }

    // Supprimer un PlusieurPaiement
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        PlusieurPaiementService.delete(id);
    }
}
