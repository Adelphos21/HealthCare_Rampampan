
package com.healthcare.healthcare.cita.repository;

import com.healthcare.healthcare.cita.entity.Cita;
import com.healthcare.healthcare.cita.entity.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByEstado(EstadoCita estado);
    Page<Cita> findByPacienteId(Long pacienteId, Pageable pageable);

    List<Cita> findByPacienteId(Long pacienteId);
}
