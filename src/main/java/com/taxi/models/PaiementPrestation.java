package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiement_prestation")
public class PaiementPrestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    @ManyToOne
    @JoinColumn(name = "id_prestation", nullable = false)
    private Prestation prestation;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    public PaiementPrestation() {}

    public PaiementPrestation(Prestation prestation, BigDecimal montant, LocalDate datePaiement) {
        this.prestation = prestation;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public Long getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Long idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
        this.prestation = prestation;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }
}
