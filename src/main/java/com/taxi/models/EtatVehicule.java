package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "etat_vehicule")
public class EtatVehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtatVehicule;

    @Column(unique = true, nullable = false)
    private String status;

    public Long getIdEtatVehicule() {
        return idEtatVehicule;
    }

    public void setIdEtatVehicule(Long idEtatVehicule) {
        this.idEtatVehicule = idEtatVehicule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
