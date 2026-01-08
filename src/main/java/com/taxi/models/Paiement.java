package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    private String status;

    @ManyToOne
    @JoinColumn(name = "id_facture", nullable = false)
    private Facture facture;

    public Long getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Long idPaiement) {
        this.idPaiement = idPaiement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}
