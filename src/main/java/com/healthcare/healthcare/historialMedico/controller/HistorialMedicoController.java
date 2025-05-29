package com.healthcare.healthcare.historialMedico.controller;

import com.healthcare.healthcare.historialMedico.dto.HistorialMedicoRequest;
import com.healthcare.healthcare.historialMedico.dto.HistorialMedicoResponse;
import com.healthcare.healthcare.historialMedico.service.HistorialMedicoService;
import com.healthcare.healthcare.paciente.repository.PacienteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@RequiredArgsConstructor
public class HistorialMedicoController {

    private final HistorialMedicoService historialService;
    private final PacienteRepository pacienteRepository;

    // Crear un historial médico
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<HistorialMedicoResponse> crear(@Valid @RequestBody HistorialMedicoRequest request) {
        if (!pacienteRepository.existsById(request.getPacienteId())) {
            return ResponseEntity.notFound().build(); // 404 si el paciente no existe
        }

        HistorialMedicoResponse response = historialService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    @GetMapping("/paciente/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<List<HistorialMedicoResponse>> listarPorPaciente(@PathVariable Long id) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 si el paciente no existe
        }

        List<HistorialMedicoResponse> historiales = historialService.listarPorPaciente(id);
        return ResponseEntity.ok(historiales); // 200 OK
    }
}