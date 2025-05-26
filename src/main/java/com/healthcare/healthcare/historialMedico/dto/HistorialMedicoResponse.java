package com.healthcare.healthcare.historialMedico.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistorialMedicoResponse {
    private Long id;
    private Long pacienteId;
    private String diagnostico;
    private String tratamiento;
    private LocalDateTime fechaRegistro;
}