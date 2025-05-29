package com.healthcare.healthcare.pago.controller;

import com.healthcare.healthcare.pago.dto.PagoRequest;
import com.healthcare.healthcare.pago.dto.PagoResponse;
import com.healthcare.healthcare.pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<PagoResponse> crear(@Valid @RequestBody PagoRequest request) {
        PagoResponse response = pagoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cita/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<List<PagoResponse>> listarPorCita(@PathVariable Long id) {
        List<PagoResponse> pagos = pagoService.listarPorCita(id);
        if (pagos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagos);
    }
}
