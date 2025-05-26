package com.healthcare.healthcare.enfermero.controller;

import com.healthcare.healthcare.enfermero.dto.EnfermeroRequest;
import com.healthcare.healthcare.enfermero.dto.EnfermeroResponse;
import com.healthcare.healthcare.enfermero.service.EnfermeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enfermeros")
@RequiredArgsConstructor
public class EnfermeroController {

    private final EnfermeroService enfermeroService;

    @PostMapping
    public EnfermeroResponse registrar(@RequestBody EnfermeroRequest request) {
        return enfermeroService.registrar(request);
    }

    @GetMapping
    public List<EnfermeroResponse> listar() {
        return enfermeroService.listar();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        enfermeroService.eliminar(id);
    }
}