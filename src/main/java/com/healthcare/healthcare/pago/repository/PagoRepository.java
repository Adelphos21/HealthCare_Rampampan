package com.healthcare.healthcare.pago.repository;

import com.healthcare.healthcare.pago.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByCitaId(Long citaId);
}
