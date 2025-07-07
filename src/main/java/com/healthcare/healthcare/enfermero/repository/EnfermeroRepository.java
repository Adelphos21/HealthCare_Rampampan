package com.healthcare.healthcare.enfermero.repository;

import com.healthcare.healthcare.enfermero.entity.Enfermero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnfermeroRepository extends JpaRepository<Enfermero, Long> {
    boolean existsByDni(String dni);
    boolean existsById(Long Id);
    Optional<Enfermero> findByDni(String dni);
    List<Enfermero> findByMedicoId(Long medicoId);
}