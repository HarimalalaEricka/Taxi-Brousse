package com.taxi.controller;

import com.taxi.models.Trajet;
import com.taxi.service.TrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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
    @GetMapping
    public List<Trajet> getAll() {
        return TrajetService.getAll();
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
}
