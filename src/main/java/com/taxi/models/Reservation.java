package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_facture", nullable = false)
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    // AJOUT: Relation inverse avec Billet
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Billet> billets;

    // AJOUT: Relation avec NbrPlaceReservation
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NbrPlaceReservation> nbrPlaceReservations;

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

    public List<Billet> getBillets() {
        return billets;
    }

    public void setBillets(List<Billet> billets) {
        this.billets = billets;
    }

    public List<NbrPlaceReservation> getNbrPlaceReservations() {
        return nbrPlaceReservations;
    }

    public void setNbrPlaceReservations(List<NbrPlaceReservation> nbrPlaceReservations) {
        this.nbrPlaceReservations = nbrPlaceReservations;
    }
}