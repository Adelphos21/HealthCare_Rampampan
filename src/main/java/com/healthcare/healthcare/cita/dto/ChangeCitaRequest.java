package com.healthcare.healthcare.cita.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChangeCitaRequest {

    @NotNull(message = "La nueva fecha de la cita es obligatoria")
    @Future(message = "La nueva fecha debe ser una fecha futura")
    private LocalDate nuevaFecha;
}
