package com.taxi.controller;

import com.taxi.models.Billet;
import com.taxi.service.BilletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Billet") // à adapter pour chaque entité, ex: /api/voyages
public class BilletController {

    @Autowired
    private BilletService BilletService;

    // Créer une entité
    @PostMapping
    public Billet create(@RequestBody Billet Billet) {
        return BilletService.create(Billet);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Billet> getAll() {
        return BilletService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Billet getById(@PathVariable Long id) {
        return BilletService.getById(id).orElse(null);
    }

    // Mettre à jour un billet
    @PutMapping("/{id}")
    public Billet update(@PathVariable Long id, @RequestBody Billet updatedBillet) {
        updatedBillet.setIdBillet(id);
        return BilletService.update(updatedBillet);
    }

    // Supprimer un billet
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        BilletService.delete(id);
    }
}
