package com.healthcare.healthcare.enfermero.controller;

import com.healthcare.healthcare.enfermero.dto.EnfermeroRequest;
import com.healthcare.healthcare.enfermero.dto.EnfermeroResponse;
import com.healthcare.healthcare.enfermero.entity.Enfermero;
import com.healthcare.healthcare.enfermero.repository.EnfermeroRepository;
import com.healthcare.healthcare.enfermero.service.EnfermeroService;
import com.healthcare.healthcare.medico.entity.Medico;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enfermeros")
@RequiredArgsConstructor
public class EnfermeroController {

    private final EnfermeroService enfermeroService;
    private final EnfermeroRepository enfermeroRepository;
    private final MedicoRepository medicoRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnfermeroResponse> registrar(@Valid @RequestBody EnfermeroRequest request) {
        EnfermeroResponse response = enfermeroService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<List<EnfermeroResponse>> listar() {
        List<EnfermeroResponse> lista = enfermeroService.listar();
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!enfermeroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enfermeroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{enfermeroId}/asignar-medico/{medicoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> asignarMedico(
            @PathVariable Long enfermeroId,
            @PathVariable Long medicoId
    ) {
        Optional<Enfermero> enfermeroOpt = enfermeroRepository.findById(enfermeroId);
        Optional<Medico> medicoOpt = medicoRepository.findById(medicoId);

        if (enfermeroOpt.isEmpty() || medicoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Enfermero enfermero = enfermeroOpt.get();
        Medico medico = medicoOpt.get();

        enfermero.setMedico(medico);
        enfermeroRepository.save(enfermero);

        return ResponseEntity.ok().body("Médico asignado correctamente");
    }

    @GetMapping("/por-medico/{medicoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<List<EnfermeroResponse>> listarPorMedico(@PathVariable Long medicoId) {
        if (!medicoRepository.existsById(medicoId)) {
            return ResponseEntity.notFound().build();
        }

        List<EnfermeroResponse> lista = enfermeroService.listarPorMedico(medicoId);
        return ResponseEntity.ok(lista);
    }
}