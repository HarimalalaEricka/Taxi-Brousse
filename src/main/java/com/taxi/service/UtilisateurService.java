package com.taxi.service;

import com.taxi.models.Utilisateur;
import com.taxi.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository UtilisateurRepository;

    public Utilisateur create(Utilisateur Utilisateur) {
        return UtilisateurRepository.save(Utilisateur);
    }

    public List<Utilisateur> getAll() {
        return UtilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getById(Long id) {
        return UtilisateurRepository.findById(id);
    }

    public Utilisateur update(Utilisateur Utilisateur) {
        return UtilisateurRepository.save(Utilisateur);
    }

    public void delete(Long id) {
        UtilisateurRepository.deleteById(id);
    }
}
