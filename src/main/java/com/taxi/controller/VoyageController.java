package com.taxi.controller;

import com.taxi.models.Voyage;
import com.taxi.service.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Voyage") // à adapter pour chaque entité, ex: /api/voyages
public class VoyageController {

    @Autowired
    private VoyageService VoyageService;

    // Créer une entité
    @PostMapping
    public Voyage create(@RequestBody Voyage Voyage) {
        return VoyageService.create(Voyage);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Voyage> getAll() {
        return VoyageService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Voyage getById(@PathVariable Long id) {
        return VoyageService.getById(id).orElse(null);
    }

    // Mettre à jour un Voyage
    @PutMapping("/{id}")
    public Voyage update(@PathVariable Long id, @RequestBody Voyage updatedVoyage) {
        updatedVoyage.setIdVoyage(id);
        return VoyageService.update(updatedVoyage);
    }

    // Supprimer un Voyage
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        VoyageService.delete(id);
    }
}
