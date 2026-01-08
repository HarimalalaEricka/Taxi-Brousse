package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "etat_paiement")
public class EtatPaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtatPaiement;

    private String etat;

    public Long getIdEtatPaiement() {
        return idEtatPaiement;
    }

    public void setIdEtatPaiement(Long idEtatPaiement) {
        this.idEtatPaiement = idEtatPaiement;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
