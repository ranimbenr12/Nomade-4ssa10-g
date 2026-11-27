package com.esprit.eventservice.repository;

import com.esprit.eventservice.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
}