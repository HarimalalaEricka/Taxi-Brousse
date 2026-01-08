package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "chauffeur")
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChauffeur;

    private String nom;
    private String prenom;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private StatutUtilisateur status;

    public Long getIdChauffeur() {
        return idChauffeur;
    }

    public void setIdChauffeur(Long idChauffeur) {
        this.idChauffeur = idChauffeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public StatutUtilisateur getStatus() {
        return status;
    }

    public void setStatus(StatutUtilisateur status) {
        this.status = status;
    }
}
