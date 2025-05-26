package com.healthcare.healthcare.enfermero.repository;

import com.healthcare.healthcare.enfermero.entity.Enfermero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnfermeroRepository extends JpaRepository<Enfermero, Long> {
    boolean existsByDni(String dni);
}