package com.taxi.repository;

import com.taxi.models.TypePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePlaceRepository extends JpaRepository<TypePlace, Long> {
}
