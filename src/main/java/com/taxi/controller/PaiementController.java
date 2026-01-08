package com.taxi.controller;

import com.taxi.models.Paiement;
import com.taxi.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Paiement") // à adapter pour chaque entité, ex: /api/voyages
public class PaiementController {

    @Autowired
    private PaiementService PaiementService;

    // Créer une entité
    @PostMapping
    public Paiement create(@RequestBody Paiement Paiement) {
        return PaiementService.create(Paiement);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Paiement> getAll() {
        return PaiementService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Paiement getById(@PathVariable Long id) {
        return PaiementService.getById(id).orElse(null);
    }

    // Mettre à jour un Paiement
    @PutMapping("/{id}")
    public Paiement update(@PathVariable Long id, @RequestBody Paiement updatedPaiement) {
        updatedPaiement.setIdPaiement(id);
        return PaiementService.update(updatedPaiement);
    }

    // Supprimer un Paiement
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        PaiementService.delete(id);
    }
}
