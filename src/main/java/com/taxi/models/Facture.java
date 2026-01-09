package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facture")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    private String numFacture;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "id_etat_paiement", nullable = false)
    private EtatPaiement etatPaiement;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    public Long getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Long idFacture) {
        this.idFacture = idFacture;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public EtatPaiement getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(EtatPaiement etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    public void genererNum() {
        String timestamp = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
        this.numFacture = "FAC_" + timestamp;
    }
}
