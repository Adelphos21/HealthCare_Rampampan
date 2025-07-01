package com.healthcare.healthcare.cita.dto;

import com.healthcare.healthcare.cita.entity.ModalidadCita;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class CitaRequest {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El ID del médico es obligatorio")
    private Long medicoId;

    @NotBlank(message = "El asunto de la cita es obligatorio")
    private String asunto;

    @NotNull(message = "La modalidad de la cita es obligatoria")
    private ModalidadCita modalidad;
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Future(message = "La fecha de la cita debe ser futura")
    private LocalDate fechaCita;
}