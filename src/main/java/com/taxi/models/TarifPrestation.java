package com.taxi.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarif_prestation")
public class TarifPrestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarifPrestation;

    private BigDecimal prixUnitaire;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "id_type_prestation", nullable = false)
    private TypePrestation typePrestation;

    public Long getIdTarifPrestation() {
        return idTarifPrestation;
    }

    public void setIdTarifPrestation(Long idTarifPrestation) {
        this.idTarifPrestation = idTarifPrestation;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public TypePrestation getTypePrestation() {
        return typePrestation;
    }

    public void setTypePrestation(TypePrestation typePrestation) {
        this.typePrestation = typePrestation;
    }
}
