package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trajet")
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrajet;

    private String villeDepart;
    private String villeArrivee;

    @Column(precision = 15, scale = 2)
    private BigDecimal distance;

    private Integer dureeEstimee;

    public Long getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(Long idTrajet) {
        this.idTrajet = idTrajet;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Integer getDureeEstimee() {
        return dureeEstimee;
    }

    public void setDureeEstimee(Integer dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }
}
