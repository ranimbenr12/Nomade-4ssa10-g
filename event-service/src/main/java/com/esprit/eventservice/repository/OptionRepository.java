package com.esprit.eventservice.repository;

import com.esprit.eventservice.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}