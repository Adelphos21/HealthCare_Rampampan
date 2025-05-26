package com.healthcare.healthcare.medico.controller;

import com.healthcare.healthcare.medico.dto.MedicoRequest;
import com.healthcare.healthcare.medico.dto.MedicoResponse;
import com.healthcare.healthcare.medico.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MedicoResponse registrar(@RequestBody MedicoRequest request) {
        return medicoService.registrar(request);
    }

    @GetMapping
    public List<MedicoResponse> listar() {
        return medicoService.listar();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
    }
}