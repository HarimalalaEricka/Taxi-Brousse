package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlace;

    private String numeroPlace;

    @Enumerated(EnumType.STRING)
    private StatusPlace statut;

    @ManyToOne
    @JoinColumn(name = "id_vehicule", nullable = false)
    private Vehicule vehicule;

    public Long getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Long idPlace) {
        this.idPlace = idPlace;
    }

    public String getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(String numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public StatusPlace getStatut() {
        return statut;
    }

    public void setStatut(StatusPlace statut) {
        this.statut = statut;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
}
