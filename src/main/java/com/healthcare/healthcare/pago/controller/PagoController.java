package com.healthcare.healthcare.pago.controller;

import com.healthcare.healthcare.pago.dto.PagoRequest;
import com.healthcare.healthcare.pago.dto.PagoResponse;
import com.healthcare.healthcare.pago.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponse> crear(@RequestBody PagoRequest request) {
        return ResponseEntity.ok(pagoService.crear(request));
    }

    @GetMapping("/cita/{id}")
    public ResponseEntity<List<PagoResponse>> listarPorCita(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.listarPorCita(id));
    }
}
