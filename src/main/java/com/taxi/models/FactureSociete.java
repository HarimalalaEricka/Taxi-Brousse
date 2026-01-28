package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Représente une facture pour une société
 * Les paiements sont effectués sur cette facture et répartis proportionnellement
 * sur toutes les prestations de la société
 */
@Entity
@Table(name = "facture_societe")
public class FactureSociete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactureSociete;

    @Column(name = "num_facture", unique = true)
    private String numFacture;

    @Column(name = "date_facture")
    private LocalDate dateFacture;

    @ManyToOne
    @JoinColumn(name = "id_societe", nullable = false)
    private Societe societe;

    @ManyToOne
    @JoinColumn(name = "id_etat_paiement")
    private EtatPaiement etatPaiement;

    @OneToMany(mappedBy = "factureSociete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaiementFactureSociete> paiements;

    @OneToMany(mappedBy = "factureSociete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestation> prestations;

    public FactureSociete() {}

    public FactureSociete(Societe societe, LocalDate dateFacture) {
        this.societe = societe;
        this.dateFacture = dateFacture;
        this.numFacture = generateNumFacture();
    }

    private String generateNumFacture() {
        return "FACT-SOC-" + System.currentTimeMillis();
    }

    // Calcule le montant total de toutes les prestations de cette facture
    public BigDecimal getMontantTotal() {
        if (prestations == null || prestations.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return prestations.stream()
                .map(Prestation::getMontantTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calcule le montant total payé sur cette facture
    public BigDecimal getMontantPaye() {
        if (paiements == null || paiements.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return paiements.stream()
                .map(PaiementFactureSociete::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calcule le reste à payer
    public BigDecimal getResteAPayer() {
        return getMontantTotal().subtract(getMontantPaye());
    }

    // Calcule le pourcentage payé
    public BigDecimal getPourcentagePaye() {
        BigDecimal total = getMontantTotal();
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getMontantPaye()
                .multiply(BigDecimal.valueOf(100))
                .divide(total, 2, java.math.RoundingMode.HALF_UP);
    }

    // Getters et Setters
    public Long getIdFactureSociete() {
        return idFactureSociete;
    }

    public void setIdFactureSociete(Long idFactureSociete) {
        this.idFactureSociete = idFactureSociete;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public EtatPaiement getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(EtatPaiement etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public List<PaiementFactureSociete> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<PaiementFactureSociete> paiements) {
        this.paiements = paiements;
    }

    public List<Prestation> getPrestations() {
        return prestations;
    }

    public void setPrestations(List<Prestation> prestations) {
        this.prestations = prestations;
    }
}
