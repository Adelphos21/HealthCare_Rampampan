package com.healthcare.healthcare.historialMedico.repository;

import com.healthcare.healthcare.historialMedico.entity.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    List<HistorialMedico> findByPacienteId(Long pacienteId);
}