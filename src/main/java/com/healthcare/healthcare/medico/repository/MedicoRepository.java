package com.healthcare.healthcare.medico.repository;

import com.healthcare.healthcare.medico.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByDni(String dni);
}