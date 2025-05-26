package com.healthcare.healthcare.historialMedico.dto;

import lombok.Data;

@Data
public class HistorialMedicoRequest {
    private Long pacienteId;
    private String diagnostico;
    private String tratamiento;
}