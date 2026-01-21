package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billet")
public class Billet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBillet;

    @Column(unique = true, nullable = false)
    private String numeroBillet;

    @ManyToOne  // CHANGÉ: de OneToOne à ManyToOne
    @JoinColumn(name = "id_reservation", nullable = false)  // SUPPRIMÉ: unique = true
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "id_place", nullable = false)
    private Place place;

    public Long getIdBillet() {
        return idBillet;
    }

    public void setIdBillet(Long idBillet) {
        this.idBillet = idBillet;
    }

    public String getNumeroBillet() {
        return numeroBillet;
    }

    public void setNumeroBillet(String numeroBillet) {
        this.numeroBillet = numeroBillet;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    
    public void genererNumeroBillet() {
        String timestamp = LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")); // Ajout des millisecondes
        this.numeroBillet = "BIL_" + timestamp + "_" + 
                           java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}