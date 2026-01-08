package com.taxi.controller;

import com.taxi.models.TypePaiement;
import com.taxi.service.TypePaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/TypePaiement") // à adapter pour chaque entité, ex: /api/voyages
public class TypePaiementController {

    @Autowired
    private TypePaiementService TypePaiementService;

    // Créer une entité
    @PostMapping
    public TypePaiement create(@RequestBody TypePaiement TypePaiement) {
        return TypePaiementService.create(TypePaiement);
    }

    // Lire toutes les entités
    @GetMapping
    public List<TypePaiement> getAll() {
        return TypePaiementService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public TypePaiement getById(@PathVariable Long id) {
        return TypePaiementService.getById(id).orElse(null);
    }

    // Mettre à jour un TypePaiement
    @PutMapping("/{id}")
    public TypePaiement update(@PathVariable Long id, @RequestBody TypePaiement updatedTypePaiement) {
        updatedTypePaiement.setIdTypePaiement(id);
        return TypePaiementService.update(updatedTypePaiement);
    }

    // Supprimer un TypePaiement
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        TypePaiementService.delete(id);
    }
}
