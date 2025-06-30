package com.healthcare.healthcare.cita.dto;

import com.healthcare.healthcare.cita.entity.EstadoCita;
import com.healthcare.healthcare.cita.entity.ModalidadCita;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CitaResponse {
    private Long id;
    private String asunto;
    private String nombrePaciente;
    private String nombreMedico;
    private LocalDate fechaReserva;
    private LocalDate fechaCita;
    private ModalidadCita modalidad;
    private EstadoCita estado;
}