package com.healthcare.healthcare.pago.service;

import com.healthcare.healthcare.pago.dto.PagoRequest;
import com.healthcare.healthcare.pago.dto.PagoResponse;

import java.util.List;

public interface PagoService {
    PagoResponse crear(PagoRequest request);
    List<PagoResponse> listarPorCita(Long citaId);
}
