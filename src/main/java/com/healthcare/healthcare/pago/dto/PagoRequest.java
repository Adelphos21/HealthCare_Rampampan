package com.healthcare.healthcare.pago.dto;

import com.healthcare.healthcare.pago.entity.MetodoPago;
import lombok.Data;

@Data
public class PagoRequest {
    private Long citaId;
    private Double monto;
    private MetodoPago metodo;
    private Boolean pagado;
}
