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

    // Calcule le montant total de la prestation
    public BigDecimal getMontantTotal() {
        if (tarifPrestation == null || quantite == null) {
            return BigDecimal.ZERO;
        }
        return tarifPrestation.getPrixUnitaire().multiply(BigDecimal.valueOf(quantite));
    }
}
