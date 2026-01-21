package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "nbr_Place_Reservation")
public class NbrPlaceReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNbrPlaceReservation;

    private int nbrPlace;

    @ManyToOne  
    @JoinColumn(name = "id_reservation", nullable = false)  
    private Reservation reservation;

    @ManyToOne 
    @JoinColumn(name = "id_categorie_personne", nullable = false)  
    private CategoriePersonne categoriePersonne;

    // Constructeurs
    public NbrPlaceReservation() {
    }

    public NbrPlaceReservation(int nbrPlace, Reservation reservation, CategoriePersonne categoriePersonne) {
        this.nbrPlace = nbrPlace;
        this.reservation = reservation;
        this.categoriePersonne = categoriePersonne;
    }

    public Long getIdNbrPlaceReservation() {
        return idNbrPlaceReservation;
    }

    public void setIdNbrPlaceReservation(Long idNbrPlaceReservation) {
        this.idNbrPlaceReservation = idNbrPlaceReservation;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public CategoriePersonne getCategoriePersonne() {
        return categoriePersonne;
    }

    public void setCategoriePersonne(CategoriePersonne categoriePersonne) {
        this.categoriePersonne = categoriePersonne;
    }
}