package com.taxi.controller;

import com.taxi.models.Facture;
import com.taxi.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Facture") // à adapter pour chaque entité, ex: /api/voyages
public class FactureController {

    @Autowired
    private FactureService FactureService;

    // Créer une entité
    @PostMapping
    public Facture create(@RequestBody Facture Facture) {
        return FactureService.create(Facture);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Facture> getAll() {
        return FactureService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Facture getById(@PathVariable Long id) {
        return FactureService.getById(id).orElse(null);
    }

    // Mettre à jour un Facture
    @PutMapping("/{id}")
    public Facture update(@PathVariable Long id, @RequestBody Facture updatedFacture) {
        updatedFacture.setIdFacture(id);
        return FactureService.update(updatedFacture);
    }

    // Supprimer un Facture
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        FactureService.delete(id);
    }
}
