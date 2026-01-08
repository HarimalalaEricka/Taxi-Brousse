package com.taxi.controller;

import com.taxi.models.Vehicule;
import com.taxi.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Vehicule") // à adapter pour chaque entité, ex: /api/voyages
public class VehiculeController {

    @Autowired
    private VehiculeService VehiculeService;

    // Créer une entité
    @PostMapping
    public Vehicule create(@RequestBody Vehicule Vehicule) {
        return VehiculeService.create(Vehicule);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Vehicule> getAll() {
        return VehiculeService.getAll();
    }

   // Lire une entité par id
    @GetMapping("/{id}")
    public Vehicule getById(@PathVariable Long id) {
        return VehiculeService.getById(id).orElse(null);
    }

    // Mettre à jour un Vehicule
    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule updatedVehicule) {
        updatedVehicule.setIdVehicule(id);
        return VehiculeService.update(updatedVehicule);
    }

    // Supprimer un Vehicule
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        VehiculeService.delete(id);
    }
}
