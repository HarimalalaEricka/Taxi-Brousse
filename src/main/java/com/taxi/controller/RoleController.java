package com.taxi.controller;

import com.taxi.models.Role;
import com.taxi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Role") // à adapter pour chaque entité, ex: /api/voyages
public class RoleController {

    @Autowired
    private RoleService RoleService;

    // Créer une entité
    @PostMapping
    public Role create(@RequestBody Role Role) {
        return RoleService.create(Role);
    }

    // Lire toutes les entités
    @GetMapping
    public List<Role> getAll() {
        return RoleService.getAll();
    }

    // Lire une entité par id
    @GetMapping("/{id}")
    public Role getById(@PathVariable Long id) {
        return RoleService.getById(id).orElse(null);
    }

    // Mettre à jour un Role
    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role updatedRole) {
        updatedRole.setIdRole(id);
        return RoleService.update(updatedRole);
    }

    // Supprimer un Role
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        RoleService.delete(id);
    }
}
