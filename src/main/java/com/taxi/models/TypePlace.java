package com.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "type_place")
public class TypePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTypePlace;

    private String type;

    public Long getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Long idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
