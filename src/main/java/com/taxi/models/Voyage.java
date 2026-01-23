package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "voyage")
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoyage;

    private LocalDate dateDepart;
    private LocalTime heureDepart;

    @ManyToOne
    @JoinColumn(name = "id_etat_voyage", nullable = false)
    private EtatVoyage etatVoyage;

    @ManyToOne
    @JoinColumn(name = "id_trajet", nullable = false)
    private Trajet trajet;

    @ManyToOne
    @JoinColumn(name = "id_chauffeur", nullable = false)
    private Chauffeur chauffeur;

    @ManyToOne
    @JoinColumn(name = "id_vehicule", nullable = false)
    private Vehicule vehicule;

    public Long getIdVoyage() {
        return idVoyage;
    }

    public void setIdVoyage(Long idVoyage) {
        this.idVoyage = idVoyage;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public EtatVoyage getEtatVoyage() {
        return etatVoyage;
    }

    public void setEtatVoyage(EtatVoyage etatVoyage) {
        this.etatVoyage = etatVoyage;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    @OneToMany(mappedBy = "voyage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestation> prestations;

    public List<Prestation> getPrestations() {
        return prestations;
    }

    public void setPrestations(List<Prestation> prestations) {
        this.prestations = prestations;
    }
}
