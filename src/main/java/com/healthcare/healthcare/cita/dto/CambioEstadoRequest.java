
package com.healthcare.healthcare.cita.dto;

import com.healthcare.healthcare.cita.entity.EstadoCita;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambioEstadoRequest {
    @NotBlank(message = "El nuevo estado de la cita es obligatorio")
    private EstadoCita nuevoEstado;
}