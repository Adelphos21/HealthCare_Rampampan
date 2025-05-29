package com.healthcare.healthcare.cita.controller;

import com.healthcare.healthcare.cita.dto.CambioEstadoRequest;
import com.healthcare.healthcare.cita.dto.ChangeCitaRequest;
import com.healthcare.healthcare.cita.dto.CitaRequest;
import com.healthcare.healthcare.cita.dto.CitaResponse;
import com.healthcare.healthcare.cita.entity.EstadoCita;
import com.healthcare.healthcare.cita.repository.CitaRepository;
import com.healthcare.healthcare.cita.service.CitaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;
    private final CitaRepository citaRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE')")
    public ResponseEntity<CitaResponse>  registrar(@RequestBody CitaRequest request) {
        try {
            CitaResponse response = citaService.registrar(request);
            if (response == null) {
                return ResponseEntity.badRequest().build();
            }
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE', 'MEDICO')")
    public List<CitaResponse> listar() {
        try {
            return citaService.listar();
        } catch (Exception e) {
            return List.of();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!citaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        try {
            citaService.eliminar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CitaResponse cambiarEstado(
            @PathVariable Long id,
            @RequestBody CambioEstadoRequest request
    ) {
        return citaService.cambiarEstado(id, request.getNuevoEstado());
    }

    @GetMapping("/api/citas/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE', 'MEDICO')")
    public List<CitaResponse> listar(@RequestParam(required = false) EstadoCita estado) {
        if (estado != null) {
            return citaService.listarPorEstado(estado);
        }
        return citaService.listar(); // listado completo
    }
    @PatchMapping("/{id}/cambiar-fecha")
    @PreAuthorize("hasRole('ADMIN, PACIENTE')")
    public ResponseEntity<CitaResponse> cambiarFechaCita(@PathVariable Long id, @RequestBody ChangeCitaRequest request) {
        if (!citaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        try {
            CitaResponse updatedCita = citaService.cambiar(id, request);
            return ResponseEntity.ok(updatedCita); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
