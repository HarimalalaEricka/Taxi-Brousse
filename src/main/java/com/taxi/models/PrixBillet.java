package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prix_billet")
public class PrixBillet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrixBillet;

    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal prix;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private String dateFin; // VARCHAR(50) selon ta table, sinon LocalDate si tu préfères

    @ManyToOne
    @JoinColumn(name = "id_trajet", nullable = false)
    private Trajet trajet;

    // Getters et setters
    public Long getIdPrixBillet() {
        return idPrixBillet;
    }

    public void setIdPrixBillet(Long idPrixBillet) {
        this.idPrixBillet = idPrixBillet;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
}
