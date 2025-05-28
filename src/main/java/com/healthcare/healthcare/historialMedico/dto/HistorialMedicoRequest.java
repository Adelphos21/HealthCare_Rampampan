package com.healthcare.healthcare.historialMedico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HistorialMedicoRequest {

    @NotBlank(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Size(min = 10 ,max = 500, message = "El tratamiento no puede exceder los 500 caracteres")
    private String diagnostico;


    @NotBlank(message = "El tratamiento es obligatorio")
    @Size(min = 10 ,max = 500, message = "El tratamiento no puede exceder los 500 caracteres")
    private String tratamiento;
}