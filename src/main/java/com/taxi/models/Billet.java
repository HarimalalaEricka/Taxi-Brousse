package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "billet")
public class Billet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBillet;

    @Column(unique = true, nullable = false)
    private String numeroBillet;

    public Long getIdBillet() {
        return idBillet;
    }

    public void setIdBillet(Long idBillet) {
        this.idBillet = idBillet;
    }

    public String getNumeroBillet() {
        return numeroBillet;
    }

    public void setNumeroBillet(String numeroBillet) {
        this.numeroBillet = numeroBillet;
    }
}
