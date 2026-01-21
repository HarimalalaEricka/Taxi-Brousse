package com.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "facture")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    @Column(unique = true)  // Le numéro de facture doit être unique
    private String numFacture;
    
    private LocalDate date;

    private BigDecimal montant;
    
    @ManyToOne
    @JoinColumn(name = "id_etat_paiement", nullable = false)
    private EtatPaiement etatPaiement;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    // AJOUT: Relation inverse avec Reservation
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

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

    public BigDecimal getMontant() {
        return montant;
    }
    
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    public void genererNum() {
        String timestamp = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        
        // Utiliser l'ID si disponible, sinon timestamp
        String base = (this.idFacture != null) ? 
                     String.format("FAC%06d", this.idFacture) : 
                     "FAC_" + timestamp;
        
        this.numFacture = base;
    }
    
    // Méthode pour générer après l'insertion
    public void genererNumApresInsertion() {
        if (this.idFacture != null) {
            this.numFacture = String.format("FAC%06d", this.idFacture);
        } else {
            genererNum();
        }
    }
}