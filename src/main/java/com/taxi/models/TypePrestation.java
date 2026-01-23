package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "type_prestation")
public class TypePrestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypePrestation;

    private String nom;

    private String description;

    public Long getIdTypePrestation() {
        return idTypePrestation;
    }

    public void setIdTypePrestation(Long idTypePrestation) {
        this.idTypePrestation = idTypePrestation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
