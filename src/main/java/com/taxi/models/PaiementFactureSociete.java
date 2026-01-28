package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Représente un paiement effectué sur une facture société
 * Le montant sera réparti proportionnellement sur toutes les prestations
 */
@Entity
@Table(name = "paiement_facture_societe")
public class PaiementFactureSociete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiementFactureSociete;

    @ManyToOne
    @JoinColumn(name = "id_facture_societe", nullable = false)
    private FactureSociete factureSociete;

    @Column(nullable = false)
    private BigDecimal montant;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "mode_paiement")
    private String modePaiement;

    @Column(name = "reference")
    private String reference;

    public PaiementFactureSociete() {}

    public PaiementFactureSociete(FactureSociete factureSociete, BigDecimal montant, LocalDate datePaiement) {
        this.factureSociete = factureSociete;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    /**
     * Calcule le pourcentage que représente ce paiement par rapport au total de la facture
     */
    public BigDecimal getPourcentage() {
        BigDecimal totalFacture = factureSociete.getMontantTotal();
        if (totalFacture.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return montant.multiply(BigDecimal.valueOf(100))
                .divide(totalFacture, 4, java.math.RoundingMode.HALF_UP);
    }

    /**
     * Calcule le montant attribué à une prestation spécifique basé sur le pourcentage
     */
    public BigDecimal getMontantPourPrestation(Prestation prestation) {
        BigDecimal totalFacture = factureSociete.getMontantTotal();
        if (totalFacture.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        // (montant_paiement / total_facture) * montant_prestation
        return montant.multiply(prestation.getMontantTotal())
                .divide(totalFacture, 2, java.math.RoundingMode.HALF_UP);
    }

    // Getters et Setters
    public Long getIdPaiementFactureSociete() {
        return idPaiementFactureSociete;
    }

    public void setIdPaiementFactureSociete(Long idPaiementFactureSociete) {
        this.idPaiementFactureSociete = idPaiementFactureSociete;
    }

    public FactureSociete getFactureSociete() {
        return factureSociete;
    }

    public void setFactureSociete(FactureSociete factureSociete) {
        this.factureSociete = factureSociete;
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

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
