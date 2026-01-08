package com.taxi.controller;

import com.taxi.models.Utilisateur;
import com.taxi.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Utilisateur") // à adapter pour chaque entité, ex: /api/voyages
public class UtilisateurController {

    @Autowired
    private UtilisateurService UtilisateurService;

    // Créer une entité
    @PostMapping
    public Utilisateur create(@RequestBody Utilisateur Utilisateur) {
        return UtilisateurService.create(Utilisateur);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Utilisateur> getAll() {
        return UtilisateurService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id) {
        return UtilisateurService.getById(id).orElse(null);
    }

    // Mettre à jour un Utilisateur
    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Long id, @RequestBody Utilisateur updatedUtilisateur) {
        updatedUtilisateur.setIdUtilisateur(id);
        return UtilisateurService.update(updatedUtilisateur);
    }

    // Supprimer un Utilisateur
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        UtilisateurService.delete(id);
    }
}
