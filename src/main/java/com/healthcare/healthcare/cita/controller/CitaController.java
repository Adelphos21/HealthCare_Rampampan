package com.healthcare.healthcare.cita.controller;

import com.healthcare.healthcare.cita.dto.CambioEstadoRequest;
import com.healthcare.healthcare.cita.dto.ChangeCitaRequest;
import com.healthcare.healthcare.cita.dto.CitaRequest;
import com.healthcare.healthcare.cita.dto.CitaResponse;
import com.healthcare.healthcare.cita.entity.EstadoCita;
import com.healthcare.healthcare.cita.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<CitaResponse> registrar(@Valid @RequestBody CitaRequest request) {
        CitaResponse response = citaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitaResponse>> listar() {
        return ResponseEntity.ok(citaService.listar()); // 200
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!citaService.existePorId(id)) {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
        citaService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CitaResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody @Valid CambioEstadoRequest request
    ) {
        if (!citaService.existePorId(id)) {
            return ResponseEntity.notFound().build(); // 404
        }
        CitaResponse response = citaService.cambiarEstado(id, request.getNuevoEstado());
        return ResponseEntity.ok(response); // 200
    }

    @GetMapping("/estado")
    public ResponseEntity<List<CitaResponse>> listarPorEstado(
            @RequestParam(required = false) EstadoCita estado
    ) {
        if (estado != null) {
            return ResponseEntity.ok(citaService.listarPorEstado(estado)); // 200
        }
        return ResponseEntity.ok(citaService.listar()); // 200
    }

    @PatchMapping("/{id}/cambiar-fecha")
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<CitaResponse> cambiarFechaCita(
            @PathVariable Long id,
            @RequestBody @Valid ChangeCitaRequest request
    ) {
        if (!citaService.existePorId(id)) {
            return ResponseEntity.notFound().build(); // 404
        }
        CitaResponse response = citaService.cambiar(id, request);
        return ResponseEntity.ok(response); // 200
    }
}
