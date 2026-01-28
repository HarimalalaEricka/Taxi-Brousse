package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prestation")
public class Prestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestation;

    private LocalDate datePrestation;

    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "id_societe", nullable = false)
    private Societe societe;

    @ManyToOne
    @JoinColumn(name = "id_tarif_prestation", nullable = false)
    private TarifPrestation tarifPrestation;

    @ManyToOne
    @JoinColumn(name = "id_etat_paiement", nullable = false)
    private EtatPaiement etatPaiement;

    @ManyToOne
    @JoinColumn(name = "id_voyage")
    private Voyage voyage;

    @ManyToOne
    @JoinColumn(name = "id_facture_societe")
    private FactureSociete factureSociete;

    public Long getIdPrestation() {
        return idPrestation;
    }

    public void setIdPrestation(Long idPrestation) {
        this.idPrestation = idPrestation;
    }

    public LocalDate getDatePrestation() {
        return datePrestation;
    }

    public void setDatePrestation(LocalDate datePrestation) {
        this.datePrestation = datePrestation;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public TarifPrestation getTarifPrestation() {
        return tarifPrestation;
    }

    public void setTarifPrestation(TarifPrestation tarifPrestation) {
        this.tarifPrestation = tarifPrestation;
    }

    public EtatPaiement getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(EtatPaiement etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public FactureSociete getFactureSociete() {
        return factureSociete;
    }

    public void setFactureSociete(FactureSociete factureSociete) {
        this.factureSociete = factureSociete;
    }

    // Calcule le montant total de la prestation
    public BigDecimal getMontantTotal() {
        if (tarifPrestation == null || quantite == null) {
            return BigDecimal.ZERO;
        }
        return tarifPrestation.getPrixUnitaire().multiply(BigDecimal.valueOf(quantite));
    }

    /**
     * Calcule le montant payé pour cette prestation basé sur les paiements de la facture société
     * Le montant est calculé proportionnellement: (paiement_total / total_facture) * montant_prestation
     */
    public BigDecimal getMontantPaye() {
        if (factureSociete == null || factureSociete.getPaiements() == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalFacture = factureSociete.getMontantTotal();
        if (totalFacture.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalPaiements = factureSociete.getMontantPaye();
        BigDecimal montantPrestation = getMontantTotal();
        
        // Montant payé = (total_paiements / total_facture) * montant_prestation
        return totalPaiements.multiply(montantPrestation)
                .divide(totalFacture, 2, java.math.RoundingMode.HALF_UP);
    }

    /**
     * Calcule le pourcentage payé pour cette prestation
     */
    public BigDecimal getPourcentagePaye() {
        if (factureSociete == null) {
            return BigDecimal.ZERO;
        }
        return factureSociete.getPourcentagePaye();
    }

    /**
     * Calcule le reste à payer pour cette prestation
     */
    public BigDecimal getResteAPayer() {
        return getMontantTotal().subtract(getMontantPaye());
    }
}
