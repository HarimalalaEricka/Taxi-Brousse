package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "place_voyage")
public class PlaceVoyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlaceVoyage;

    @ManyToOne
    @JoinColumn(name = "id_place", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    @Enumerated(EnumType.STRING)
    private StatusPlace statut;

    public PlaceVoyage() {
    }

    public PlaceVoyage(Place place, Voyage voyage, StatusPlace statut) {
        this.place = place;
        this.voyage = voyage;
        this.statut = statut;
    }

    public Long getIdPlaceVoyage() {
        return idPlaceVoyage;
    }

    public void setIdPlaceVoyage(Long idPlaceVoyage) {
        this.idPlaceVoyage = idPlaceVoyage;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public StatusPlace getStatut() {
        return statut;
    }

    public void setStatut(StatusPlace statut) {
        this.statut = statut;
    }
}
