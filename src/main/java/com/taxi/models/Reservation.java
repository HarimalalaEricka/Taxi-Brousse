package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDateTime dateReservation;

    @Enumerated(EnumType.STRING)
    private StatusReservation statut;

    private String auNomDe;

    @OneToOne
    @JoinColumn(name = "id_billet", unique = true, nullable = false)
    private Billet billet;

    @ManyToOne
    @JoinColumn(name = "id_facture", nullable = false)
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    @OneToOne
    @JoinColumn(name = "id_place", unique = true, nullable = false)
    private Place place;

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public StatusReservation getStatut() {
        return statut;
    }

    public void setStatut(StatusReservation statut) {
        this.statut = statut;
    }

    public String getAuNomDe() {
        return auNomDe;
    }

    public void setAuNomDe(String auNomDe) {
        this.auNomDe = auNomDe;
    }

    public Billet getBillet() {
        return billet;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
