package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "type_paiement")
public class TypePaiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypePaiement;

    @Column(unique = true, nullable = false)
    private String type;

    public Long getIdTypePaiement() {
        return idTypePaiement;
    }

    public void setIdTypePaiement(Long idTypePaiement) {
        this.idTypePaiement = idTypePaiement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
