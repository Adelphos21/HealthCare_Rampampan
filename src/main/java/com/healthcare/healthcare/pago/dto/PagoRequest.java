package com.healthcare.healthcare.pago.dto;

import com.healthcare.healthcare.pago.entity.MetodoPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PagoRequest {

    @NotNull(message = "El ID de la cita no puede ser nulo")
    @Positive(message = "El ID de la cita debe ser un número positivo")
    private Long citaId;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "El método de pago no puede ser nulo")
    private MetodoPago metodo;

    @NotNull(message = "El campo pagado no puede ser nulo")
    private Boolean pagado;
}
