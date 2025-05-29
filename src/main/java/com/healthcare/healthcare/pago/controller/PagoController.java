package com.healthcare.healthcare.pago.controller;

import com.healthcare.healthcare.pago.dto.PagoRequest;
import com.healthcare.healthcare.pago.dto.PagoResponse;
import com.healthcare.healthcare.pago.service.PagoService;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('PACIENTE')")
    public ResponseEntity<PagoResponse> crear(@RequestBody PagoRequest request) {
        PagoResponse response = pagoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/cita/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PACIENTE')")
    public ResponseEntity<List<PagoResponse>> listarPorCita(@PathVariable Long id) {
        List<PagoResponse> pagos = pagoService.listarPorCita(id);
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PACIENTE')")
    public ResponseEntity<PagoResponse> actualizar(@PathVariable Long id, @RequestBody PagoRequest request) {
        PagoResponse response = pagoService.actualizar(id, request);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!pagoService.existePago(id)) {
            return ResponseEntity.notFound().build();
        }
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
