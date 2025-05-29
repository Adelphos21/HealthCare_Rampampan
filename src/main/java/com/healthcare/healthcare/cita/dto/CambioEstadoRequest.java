
package com.healthcare.healthcare.cita.dto;

import com.healthcare.healthcare.cita.entity.EstadoCita;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioEstadoRequest {

    @NotNull(message = "El nuevo estado de la cita es obligatorio")
    private EstadoCita nuevoEstado;
}