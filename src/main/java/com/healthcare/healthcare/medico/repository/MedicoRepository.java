package com.healthcare.healthcare.medico.repository;

import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.medico.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByDni(String dni);
    boolean existsByCorreo(String dni);

    List<Medico> findByEspecialidad(Especialidad especialidad);
    List<Medico> findByNombreContainingIgnoreCase(String nombre);
}