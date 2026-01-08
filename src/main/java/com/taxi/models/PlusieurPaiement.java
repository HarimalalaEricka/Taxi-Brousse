package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plusieur_paiement")
public class PlusieurPaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlusieurPaiement;

    private LocalDateTime datePaiement;

    @Column(precision = 15, scale = 2)
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "id_paiement", nullable = false)
    private Paiement paiement;

    @ManyToOne
    @JoinColumn(name = "id_type_paiement", nullable = false)
    private TypePaiement typePaiement;

    public Long getIdPlusieurPaiement() {
        return idPlusieurPaiement;
    }

    public void setIdPlusieurPaiement(Long idPlusieurPaiement) {
        this.idPlusieurPaiement = idPlusieurPaiement;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }
}
