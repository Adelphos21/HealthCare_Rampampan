package com.healthcare.healthcare.medico.repository;

import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.paciente.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByDni(String dni);
    boolean existsByCorreo(String dni);
    Optional<Medico> findByDni(String dni);
    List<Medico> findByEspecialidad(Especialidad especialidad);
    List<Medico> findByNombreContainingIgnoreCase(String nombre);
}