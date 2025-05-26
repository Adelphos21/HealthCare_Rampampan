package com.healthcare.healthcare.cita.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CitaRequest {
    private Long pacienteId;
    private Long medicoId;
    private String asunto;
    private LocalDate fechaCita;
}