package com.esprit.eventservice.repository;

import com.esprit.eventservice.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Long> {
}