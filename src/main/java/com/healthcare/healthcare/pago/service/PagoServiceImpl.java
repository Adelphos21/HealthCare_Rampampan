package com.healthcare.healthcare.pago.service;

import com.healthcare.healthcare.cita.entity.Cita;
import com.healthcare.healthcare.cita.repository.CitaRepository;
import com.healthcare.healthcare.pago.dto.PagoRequest;
import com.healthcare.healthcare.pago.dto.PagoResponse;
import com.healthcare.healthcare.pago.entity.Pago;
import com.healthcare.healthcare.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;

    @Override
    public PagoResponse crear(PagoRequest request) {
        Cita cita = citaRepository.findById(request.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        Pago pago = Pago.builder()
                .cita(cita)
                .monto(request.getMonto())
                .metodoPago(request.getMetodo())
                .pagado(request.getPagado())
                .build();

        pago = pagoRepository.save(pago);

        return toResponse(pago);
    }

    @Override
    public List<PagoResponse> listarPorCita(Long citaId) {
        return pagoRepository.findByCitaId(citaId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PagoResponse toResponse(Pago pago) {
        return PagoResponse.builder()
                .id(pago.getId())
                .citaId(pago.getCita().getId())
                .monto(pago.getMonto())
                .metodo(pago.getMetodoPago())
                .pagado(pago.getPagado())
                .fechaPago(pago.getFechaPago())
                .build();
    }
}
