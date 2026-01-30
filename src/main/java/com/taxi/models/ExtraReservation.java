package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "extra_reservation")
public class ExtraReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExtraReservation;

    @ManyToOne
    @JoinColumn(name = "id_extra")
    private Extra extra;

    @ManyToOne
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;


    private Integer quantite;

    // Date de la réservation (copie de Reservation.dateReservation pour le filtrage rapide)
    private java.time.LocalDateTime dateReservation;

    // Getters et setters
    public Long getIdExtraReservation() { return idExtraReservation; }
    public void setIdExtraReservation(Long idExtraReservation) { this.idExtraReservation = idExtraReservation; }
    public Extra getExtra() { return extra; }
    public void setExtra(Extra extra) { this.extra = extra; }
    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public java.time.LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(java.time.LocalDateTime dateReservation) { this.dateReservation = dateReservation; }
}
