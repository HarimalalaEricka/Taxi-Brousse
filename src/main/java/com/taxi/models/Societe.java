package com.taxi.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "societe")
public class Societe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSociete;

    private String nom;

    private String adresse;

    private String contact;

    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestation> prestations;

    public Long getIdSociete() {
        return idSociete;
    }

    public void setIdSociete(Long idSociete) {
        this.idSociete = idSociete;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Prestation> getPrestations() {
        return prestations;
    }

    public void setPrestations(List<Prestation> prestations) {
        this.prestations = prestations;
    }
}
