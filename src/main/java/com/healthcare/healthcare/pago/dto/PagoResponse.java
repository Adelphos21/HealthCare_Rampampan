package com.healthcare.healthcare.pago.dto;

import com.healthcare.healthcare.pago.entity.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponse {
    private Long id;
    private Long citaId;
    private Double monto;
    private MetodoPago metodo;
    private Boolean pagado;
    private LocalDateTime fechaPago;
}
