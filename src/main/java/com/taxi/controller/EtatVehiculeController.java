package com.taxi.controller;

import com.taxi.models.EtatVehicule;
import com.taxi.service.EtatVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/EtatVehicule") // à adapter pour chaque entité, ex: /api/voyages
public class EtatVehiculeController {

    @Autowired
    private EtatVehiculeService EtatVehiculeService;

    // Créer une entité
    @PostMapping
    public EtatVehicule create(@RequestBody EtatVehicule EtatVehicule) {
        return EtatVehiculeService.create(EtatVehicule);
    }

    // Lire toutes les entités
    @GetMapping
    public List<EtatVehicule> getAll() {
        return EtatVehiculeService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public EtatVehicule getById(@PathVariable Long id) {
        return EtatVehiculeService.getById(id).orElse(null);
    }

    // Mettre à jour un EtatVehicule
    @PutMapping("/{id}")
    public EtatVehicule update(@PathVariable Long id, @RequestBody EtatVehicule updatedEtatVehicule) {
        updatedEtatVehicule.setIdEtatVehicule(id);
        return EtatVehiculeService.update(updatedEtatVehicule);
    }

    // Supprimer un EtatVehicule
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        EtatVehiculeService.delete(id);
    }
}
