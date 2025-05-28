package com.healthcare.healthcare.paciente.repository;

import com.healthcare.healthcare.paciente.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByDni(String dni);
    Optional<Paciente> findByUsername(String correo);
    boolean existsByDni(String dni);
}
