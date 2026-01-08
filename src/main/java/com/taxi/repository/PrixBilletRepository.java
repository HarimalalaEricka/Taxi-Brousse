package com.taxi.repository;

import com.taxi.models.PrixBillet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrixBilletRepository extends JpaRepository<PrixBillet, Long> {
}
