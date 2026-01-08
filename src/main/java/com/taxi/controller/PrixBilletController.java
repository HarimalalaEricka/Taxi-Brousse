package com.taxi.controller;

import com.taxi.models.PrixBillet;
import com.taxi.service.PrixBilletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/PrixBillet") // à adapter pour chaque entité, ex: /api/voyages
public class PrixBilletController {

    @Autowired
    private PrixBilletService PrixBilletService;

    // Créer une entité
    @PostMapping
    public PrixBillet create(@RequestBody PrixBillet PrixBillet) {
        return PrixBilletService.create(PrixBillet);
    }

    // Lire toutes les entités
    @GetMapping
    public List<PrixBillet> getAll() {
        return PrixBilletService.getAll();
    }

   // Lire une entité par id
    @GetMapping("/{id}")
    public PrixBillet getById(@PathVariable Long id) {
        return PrixBilletService.getById(id).orElse(null);
    }

    // Mettre à jour un PrixBillet
    @PutMapping("/{id}")
    public PrixBillet update(@PathVariable Long id, @RequestBody PrixBillet updatedPrixBillet) {
        updatedPrixBillet.setIdPrixBillet(id);
        return PrixBilletService.update(updatedPrixBillet);
    }

    // Supprimer un PrixBillet
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        PrixBilletService.delete(id);
    }
}
