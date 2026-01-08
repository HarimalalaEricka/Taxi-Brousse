package com.taxi.controller;

import com.taxi.models.EtatPaiement;
import com.taxi.service.EtatPaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/EtatPaiement") // à adapter pour chaque entité, ex: /api/voyages
public class EtatPaiementController {

    @Autowired
    private EtatPaiementService EtatPaiementService;

    // Créer une entité
    @PostMapping
    public EtatPaiement create(@RequestBody EtatPaiement EtatPaiement) {
        return EtatPaiementService.create(EtatPaiement);
    }

    // Lire toutes les entités
    @GetMapping
    public List<EtatPaiement> getAll() {
        return EtatPaiementService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public EtatPaiement getById(@PathVariable Long id) {
        return EtatPaiementService.getById(id).orElse(null);
    }

    // Mettre à jour un EtatPaiement
    @PutMapping("/{id}")
    public EtatPaiement update(@PathVariable Long id, @RequestBody EtatPaiement updatedEtatPaiement) {
        updatedEtatPaiement.setIdEtatPaiement(id);
        return EtatPaiementService.update(updatedEtatPaiement);
    }

    // Supprimer un EtatPaiement
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        EtatPaiementService.delete(id);
    }
}
