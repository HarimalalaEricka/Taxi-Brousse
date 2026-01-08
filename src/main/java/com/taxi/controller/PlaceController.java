package com.taxi.controller;

import com.taxi.models.Place;
import com.taxi.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Place") // à adapter pour chaque entité, ex: /api/voyages
public class PlaceController {

    @Autowired
    private PlaceService PlaceService;

    // Créer une entité
    @PostMapping
    public Place create(@RequestBody Place Place) {
        return PlaceService.create(Place);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Place> getAll() {
        return PlaceService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Place getById(@PathVariable Long id) {
        return PlaceService.getById(id).orElse(null);
    }

    // Mettre à jour un Place
    @PutMapping("/{id}")
    public Place update(@PathVariable Long id, @RequestBody Place updatedPlace) {
        updatedPlace.setIdPlace(id);
        return PlaceService.update(updatedPlace);
    }

    // Supprimer un Place
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        PlaceService.delete(id);
    }
}
