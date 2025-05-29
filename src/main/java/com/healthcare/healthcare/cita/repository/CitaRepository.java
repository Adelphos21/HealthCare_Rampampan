
package com.healthcare.healthcare.cita.repository;

import com.healthcare.healthcare.cita.entity.Cita;
import com.healthcare.healthcare.cita.entity.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByEstado(EstadoCita estado);
    List<Cita> findByPacienteId(Long pacienteId);

}
