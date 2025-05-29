package com.healthcare.healthcare.historialMedico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HistorialMedicoRequest {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotBlank(message = "El diagnóstico no puede estar vacío")
    private String diagnostico;

    @NotBlank(message = "El tratamiento no puede estar vacío")
    private String tratamiento;
}