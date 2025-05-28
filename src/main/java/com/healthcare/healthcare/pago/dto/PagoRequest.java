package com.healthcare.healthcare.pago.dto;

import com.healthcare.healthcare.pago.entity.MetodoPago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PagoRequest {
    @NotBlank(message = "El ID de la cita es obligatorio")
    private Long citaId;


    @NotBlank(message = "El monto es obligatorio")
    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Double monto;


    @NotBlank(message = "El método de pago es obligatorio")
    private MetodoPago metodo;


    @NotBlank(message = "El estado de pago es obligatorio")
    private Boolean pagado;
}
