package com.healthcare.healthcare.medico.controller;

import com.healthcare.healthcare.medico.dto.MedicoRequest;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.entity.Especialidad;
import com.healthcare.healthcare.medico.repository.MedicoRepository;
import com.healthcare.healthcare.medico.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;
    private final MedicoRepository medicoRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicoResponse> registrar(@Valid @RequestBody MedicoRequest request) {
        MedicoResponse response = medicoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MedicoResponse>> listar() {
        List<MedicoResponse> medicos = medicoService.listar();
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/especialidad/{especialidad}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MedicoResponse>> listarPorEspecialidad(@PathVariable String nombre) {
        //Quitar tildes y minusculas
        String especialidad = Normalizer.normalize(nombre, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase();

        List<MedicoResponse> medicos = medicoService.listarPorEspecialidad(Especialidad.valueOf(especialidad));
        return ResponseEntity.ok(medicos);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        medicoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}