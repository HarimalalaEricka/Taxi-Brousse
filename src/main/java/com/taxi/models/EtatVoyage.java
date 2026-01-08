package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "etat_voyage")
public class EtatVoyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtatVoyage;

    private String etat;

    public Long getIdEtatVoyage() {
        return idEtatVoyage;
    }

    public void setIdEtatVoyage(Long idEtatVoyage) {
        this.idEtatVoyage = idEtatVoyage;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
