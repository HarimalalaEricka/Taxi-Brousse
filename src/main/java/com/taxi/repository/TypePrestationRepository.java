package com.taxi.repository;

import com.taxi.models.TypePrestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePrestationRepository extends JpaRepository<TypePrestation, Long> {
}
